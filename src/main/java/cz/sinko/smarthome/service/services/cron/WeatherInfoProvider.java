package cz.sinko.smarthome.service.services.cron;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.WeatherInfoDao;
import cz.sinko.smarthome.repository.entities.WeatherInfo;
import cz.sinko.smarthome.service.dtos.inputs.WeatherInputDto;

@Service
@Transactional
public class WeatherInfoProvider {

	private static final Logger logger = LoggerFactory.getLogger(WeatherInfoProvider.class);

	public static final String APIKEY = "c2e04a00278031fc5b8e8c78590e2939";
	public static final String URL = "api.openweathermap.org/data/2.5/weather";
	public static final String BRNO_ID = "3078610";
	public static final String UNITS = "metric";

	@Autowired
	private WeatherInfoDao weatherInfoDao;

	//TODO: save temperature and sun info separately, check sun info just once in 24 hours
	@Scheduled(fixedRate = 30 * 60 * 1000, initialDelay = 5000)
	public void checkWeatherInfo() {
		logger.info("Checking weather info");
		WeatherInputDto weatherInputDto = getWeatherInfo();
		logger.info(weatherInputDto.toString());
		WeatherInfo weatherInfo = new WeatherInfo();
		weatherInfo.setTemperature(Float.parseFloat(weatherInputDto.getTemperature().getTemperature()));
		weatherInfo.setSunrise(Date.from(Instant.ofEpochSecond(Long.parseLong(weatherInputDto.getSunriseAndSunset().getSunrise()))));
		weatherInfo.setSunset(Date.from(Instant.ofEpochSecond(Long.parseLong(weatherInputDto.getSunriseAndSunset().getSunset()))));
		weatherInfo.setTimestamp(new Date());
		weatherInfoDao.save(weatherInfo);
	}

	private WeatherInputDto getWeatherInfo() {
		final String uri = "http://" + URL + "?id=" + BRNO_ID + "&units=" + UNITS + "&APPID=" + APIKEY;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, WeatherInputDto.class);
	}

}
