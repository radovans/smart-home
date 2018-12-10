package cz.sinko.smarthome.service.services;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.repository.daos.LightingDurationDao;
import cz.sinko.smarthome.repository.entities.LightingDuration;

//TODO: calculate lightings duration while sun is up
//TODO: cover calculations with tests
//TODO: cloudiness should have role in power savings
@Service
@Transactional
public class LightingCalculationsServiceImpl implements LightingCalculationsService {

	private static final BigDecimal ELECTRCITY_PRICE_PER_KWH = new BigDecimal(3.32);
	private static final BigDecimal LED_BULB_POWER_CONSUMPTION_IN_WATTS = new BigDecimal(10);
	private static final BigDecimal NORMAL_BULB_POWER_CONSUMPTION_IN_WATTS = new BigDecimal(75);
	private static final BigDecimal WATTS_IN_KILOWATTS = new BigDecimal(1000);

	private final LightingDurationDao lightingDurationDao;

	@Autowired public LightingCalculationsServiceImpl(LightingDurationDao lightingDurationDao) {
		this.lightingDurationDao = lightingDurationDao;
	}

	//TODO: cover edge cases, when light is turned on during midnight
	@Override
	public Duration getLightingDurationByDate(LocalDate date) {
		List<LightingDuration> allLightsDurationByDate = lightingDurationDao.findAllByDate(date);
		long durationOfLightingInSeconds =
				allLightsDurationByDate.stream().mapToLong(LightingDuration::getDurationOfLightingInSeconds).sum();
		return Duration.of(durationOfLightingInSeconds, SECONDS);
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

}
