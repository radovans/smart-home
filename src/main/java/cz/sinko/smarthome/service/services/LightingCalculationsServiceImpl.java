package cz.sinko.smarthome.service.services;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.repository.daos.LightingDurationRepository;
import cz.sinko.smarthome.repository.daos.SunInfoRepository;
import cz.sinko.smarthome.repository.entities.LightingDuration;
import cz.sinko.smarthome.repository.entities.SunInfo;
import cz.sinko.smarthome.service.dtos.lighting.LightingInfoDto;

//TODO: cover calculations with tests
//TODO: cloudiness should have role in power savings
@Service
@Transactional
public class LightingCalculationsServiceImpl implements LightingCalculationsService {

	private static final BigDecimal ELECTRCITY_PRICE_PER_KWH = new BigDecimal(3.32);
	private static final BigDecimal LED_BULB_POWER_CONSUMPTION_IN_WATTS = new BigDecimal(10);
	private static final BigDecimal NORMAL_BULB_POWER_CONSUMPTION_IN_WATTS = new BigDecimal(75);
	private static final BigDecimal WATTS_IN_KILOWATTS = new BigDecimal(1000);
	private static final long RESERVE = 20l;

	private final LightingDurationRepository lightingDurationRepository;
	private final SunInfoRepository sunInfoRepository;

	@Autowired public LightingCalculationsServiceImpl(LightingDurationRepository lightingDurationRepository, SunInfoRepository sunInfoRepository) {
		this.lightingDurationRepository = lightingDurationRepository;
		this.sunInfoRepository = sunInfoRepository;
	}

	@Override
	public Duration getLightingDurationByDate(LocalDate date) {
		LocalDateTime startOfDay = date.atStartOfDay().withNano(0);
		LocalDateTime endOfDay = date.atStartOfDay().plusDays(1).withNano(0);

		Duration result = Duration.ZERO;
		result = getDurationBetween(date, startOfDay, endOfDay, result);
		return result;
	}

	private Duration getDurationBetween(LocalDate date, LocalDateTime start, LocalDateTime end, Duration result) {
		for (LightingDuration lightingDuration : lightingDurationRepository.findAllByDate(date)) {
			//Between start and end
			if (lightingDuration.getLightingFrom().isAfter(start)
					&& lightingDuration.getLightingTo().isBefore(end)) {
				result = result.plus(Duration.between(lightingDuration.getLightingFrom(),
						lightingDuration.getLightingTo()));
			}

			//Before start
			if (lightingDuration.getLightingFrom().isBefore(start)
					&& lightingDuration.getLightingTo().isAfter(start)
					&& lightingDuration.getLightingTo().isBefore(end)) {
				result = result.plus(Duration.between(start, lightingDuration.getLightingTo()));
			}

			//After end
			if (lightingDuration.getLightingFrom().isAfter(start)
					&& lightingDuration.getLightingFrom().isBefore(end)
					&& lightingDuration.getLightingTo().isAfter(end)) {
				result = result.plus(Duration.between(lightingDuration.getLightingFrom(), end));
			}

			//Before start and after end
			if (lightingDuration.getLightingFrom().isBefore(start)
					&& lightingDuration.getLightingTo().isAfter(end)) {
				result = result.plus(Duration.between(start, end));
			}
		}
		return result;
	}

	@Override
	public BigDecimal getPowerSavingsByDate(LocalDate date) {
		Duration lightingDuration = getLightingDurationByDate(date);
		BigDecimal ledConsumption =
				LED_BULB_POWER_CONSUMPTION_IN_WATTS.multiply(new BigDecimal(lightingDuration.toMinutes()).divide(new BigDecimal(60), RoundingMode.HALF_UP));
		BigDecimal normalConsumption =
				NORMAL_BULB_POWER_CONSUMPTION_IN_WATTS.multiply(new BigDecimal(lightingDuration.toMinutes()).divide(new BigDecimal(60), RoundingMode.HALF_UP));
		BigDecimal ledCosts = ledConsumption.multiply(ELECTRCITY_PRICE_PER_KWH);
		BigDecimal normalCosts = normalConsumption.multiply(ELECTRCITY_PRICE_PER_KWH);
		BigDecimal costs = (normalCosts.subtract(ledCosts)).divide(WATTS_IN_KILOWATTS, RoundingMode.HALF_UP);
		return costs.setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public Duration getLightingDurationDuringDayByDate(LocalDate date) {
		Optional<SunInfo> sunInfo = sunInfoRepository.findByDate(date);
		if (sunInfo.isPresent()) {
			LocalDateTime sunrise = sunInfo.get().getSunrise().atDate(date).plus(RESERVE, MINUTES);
			LocalDateTime sunset = sunInfo.get().getSunset().atDate(date);

			Duration result = Duration.ZERO;
			result = getDurationBetween(date, sunrise, sunset, result);
			return result;
		} else {
			return Duration.ZERO;
		}
	}

	@Override
	public LightingInfoDto getLightingInfo(LocalDate date) {
		LightingInfoDto lightingInfoDto = new LightingInfoDto();
		lightingInfoDto.setLightingDuration(getLightingDurationByDate(date));
		lightingInfoDto.setPowerSavings(getPowerSavingsByDate(date));
		lightingInfoDto.setLightingDurationDuringDay(getLightingDurationDuringDayByDate(date));
		return lightingInfoDto;
	}
}
