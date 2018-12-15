package cz.sinko.smarthome.service.services.cron;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.RoomInfoDao;
import cz.sinko.smarthome.repository.entities.RoomInfo;
import cz.sinko.smarthome.service.dtos.inputs.RoomInfoInputDto;

@Service
@Transactional
@ConditionalOnProperty(name = "feature.toggles.cron.room.info", havingValue = "true", matchIfMissing = true)
public class RoomInfoProvider {

	private static final String URL = "192.168.0.200";
	private static final Logger logger = LoggerFactory.getLogger(RoomInfoProvider.class);
	private final RoomInfoDao roomInfoDao;

	@Autowired public RoomInfoProvider(RoomInfoDao roomInfoDao) {
		this.roomInfoDao = roomInfoDao;
	}

	@Scheduled(fixedRate = 60 * 1000, initialDelay = 5000)
	public void checkRoomInfo() {
		logger.debug("Checking room info");
		RoomInfoInputDto roomInfoInputDto = null;
		try {
			roomInfoInputDto = getRoomInfo();
			RoomInfo roomInfo = new RoomInfo();
			roomInfo.setHumidity(BigDecimal.valueOf(Double.parseDouble(roomInfoInputDto.getHumidity())).setScale(2,
					RoundingMode.HALF_UP));
			roomInfo.setTemperature(BigDecimal.valueOf(Double.parseDouble(roomInfoInputDto.getTemperature())).setScale(2,
					RoundingMode.HALF_UP));
			//TODO: change ESP implementation so ESP got sensor id
			roomInfo.setSensorId("esp1");
			roomInfo.setTimestamp(LocalDateTime.now());
			roomInfoDao.save(roomInfo);
		} catch (NumberFormatException exception) {
			logger.warn("ESP is not responding data: " + roomInfoInputDto.toString());
		}
	}

	private RoomInfoInputDto getRoomInfo() {
		final String uri = "http://" + URL;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, RoomInfoInputDto.class);
	}

}
