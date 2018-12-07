package cz.sinko.smarthome.service.services.cron;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.SunInfoDao;
import cz.sinko.smarthome.repository.daos.WeatherInfoDao;
import cz.sinko.smarthome.repository.entities.SunInfo;
import cz.sinko.smarthome.repository.entities.WeatherInfo;
import cz.sinko.smarthome.service.dtos.inputs.WeatherInputDto;

@Service
@Transactional
@ConditionalOnProperty(name = "feature.toggles.cron.weather.info", havingValue = "true", matchIfMissing = true)
public class WeatherInfoProvider {

	private static final String APIKEY = "c2e04a00278031fc5b8e8c78590e2939";
	private static final String URL = "api.openweathermap.org/data/2.5/weather";
	private static final String BRNO_ID = "3078610";
	private static final String UNITS = "metric";
	private static final Logger logger = LoggerFactory.getLogger(WeatherInfoProvider.class);

	private final WeatherInfoDao weatherInfoDao;
	private final SunInfoDao sunInfoDao;

	@Autowired public WeatherInfoProvider(WeatherInfoDao weatherInfoDao, SunInfoDao sunInfoDao) {
		this.weatherInfoDao = weatherInfoDao;
		this.sunInfoDao = sunInfoDao;
	}

	@Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 5000)
	public void checkSunInfo() {
		LocalDate today = LocalDate.now();
		if (sunInfoDao.findByDate(today) == null) {
			logger.info("Checking sun info");
			WeatherInputDto weatherInputDto = getWeatherAndSunInfo();
			logger.debug(weatherInputDto.toString());
			SunInfo sunInfo = new SunInfo();
			sunInfo.setSunrise(LocalTime.from(LocalDateTime.ofInstant(
					Instant.ofEpochSecond(
							Long.parseLong(weatherInputDto.getSunriseAndSunset().getSunrise())),
					TimeZone.getDefault().toZoneId())));
			sunInfo.setSunset(LocalTime.from(LocalDateTime.ofInstant(
					Instant.ofEpochSecond(
							Long.parseLong(weatherInputDto.getSunriseAndSunset().getSunset())),
					TimeZone.getDefault().toZoneId())));
			sunInfo.setDate(today);
			sunInfoDao.save(sunInfo);
		}
	}

	@Scheduled(fixedRate = 30 * 60 * 1000, initialDelay = 5000)
	public void checkWeatherInfo() {
		logger.info("Checking weather info");
		WeatherInputDto weatherInputDto = getWeatherAndSunInfo();
		logger.debug(weatherInputDto.toString());
		WeatherInfo weatherInfo = new WeatherInfo();
		weatherInfo.setTemperature(BigDecimal.valueOf(Double.parseDouble(weatherInputDto.getTemperature().getTemperature())).setScale(2, RoundingMode.HALF_UP));
		weatherInfo.setTimestamp(LocalDateTime.now());
		weatherInfoDao.save(weatherInfo);
	}

	private WeatherInputDto getWeatherAndSunInfo() {
		final String uri = "http://" + URL + "?id=" + BRNO_ID + "&units=" + UNITS + "&APPID=" + APIKEY;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, WeatherInputDto.class);
	}

}
