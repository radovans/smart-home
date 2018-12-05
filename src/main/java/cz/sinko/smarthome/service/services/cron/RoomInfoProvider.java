package cz.sinko.smarthome.service.services.cron;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.RoomInfoDao;
import cz.sinko.smarthome.repository.entities.RoomInfo;
import cz.sinko.smarthome.service.dtos.inputs.RoomInfoInputDto;

@Service
@Transactional
public class RoomInfoProvider {

	private static final Logger logger = LoggerFactory.getLogger(RoomInfoProvider.class);

	public static final String URL = "url";

	@Autowired
	private RoomInfoDao roomInfoDao;

	//	@Scheduled(fixedRate = 60 * 1000, initialDelay = 5000)
	public void checkRoomInfo() {
		logger.debug("Checking room info");
		RoomInfoInputDto roomInfoInputDto = getRoomInfo();
		logger.debug(roomInfoInputDto.toString());
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setHumidity(BigDecimal.valueOf(Double.parseDouble(roomInfoInputDto.getHumidity())).setScale(2,
				RoundingMode.HALF_UP));
		roomInfo.setTemperature(BigDecimal.valueOf(Double.parseDouble(roomInfoInputDto.getTemperature())).setScale(2,
				RoundingMode.HALF_UP));
		roomInfo.setSensorId(roomInfoInputDto.getSensorId());
		roomInfo.setTimestamp(LocalDateTime.now());
		roomInfoDao.save(roomInfo);
	}

	private RoomInfoInputDto getRoomInfo() {
		final String uri = "http://" + URL;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, RoomInfoInputDto.class);
	}

}