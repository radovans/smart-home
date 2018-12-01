package cz.sinko.smarthome.service.services;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.repository.daos.LightInfoDao;
import cz.sinko.smarthome.repository.entities.LightInfo;

@Service
@Transactional
public class LightingCalculationsServiceImpl implements LightingCalculationsService {

	public static final BigDecimal ELECTRCITY_PRICE_PER_KWH = new BigDecimal(3.32);
	public static final BigDecimal LED_BULB_POWER_CONSUMPTION_IN_WATTS = new BigDecimal(10);
	public static final BigDecimal NORMAL_BULB_POWER_CONSUMPTION_IN_WATTS = new BigDecimal(75);
	public static final BigDecimal WATTS_IN_KILOWATTS = new BigDecimal(1000);

	@Autowired
	private LightInfoDao lightInfoDao;

	@Override
	public Duration getLightingDurationByDate(Date date) {
		List<LightInfo> allLightsInfoByDate = lightInfoDao.findAllByTimestamp(date);
		long durationOfLightingInSeconds =
				allLightsInfoByDate.stream().mapToLong(lightInfo -> lightInfo.getDurationOfLighting().getSeconds()).sum();
		return Duration.of(durationOfLightingInSeconds, SECONDS);
	}

	@Override
	public BigDecimal getPowerSavingsByDate(Date date) {
		Duration lightingDuration = getLightingDurationByDate(date);
		BigDecimal ledConsumption =
				LED_BULB_POWER_CONSUMPTION_IN_WATTS.multiply(new BigDecimal(lightingDuration.toMinutes()).divide(new BigDecimal(60)));
		BigDecimal normalConsumption =
				NORMAL_BULB_POWER_CONSUMPTION_IN_WATTS.multiply(new BigDecimal(lightingDuration.toMinutes()).divide(new BigDecimal(60)));
		BigDecimal ledCosts = ledConsumption.multiply(ELECTRCITY_PRICE_PER_KWH);
		BigDecimal normalCosts = normalConsumption.multiply(ELECTRCITY_PRICE_PER_KWH);
		BigDecimal costs = (normalCosts.subtract(ledCosts)).divide(WATTS_IN_KILOWATTS);
		return costs.setScale(2, RoundingMode.HALF_UP);
	}

}
