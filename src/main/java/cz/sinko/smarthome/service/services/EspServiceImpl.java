package cz.sinko.smarthome.service.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.config.mapper.OrikaBeanMapper;
import cz.sinko.smarthome.repository.daos.RoomInfoRepository;
import cz.sinko.smarthome.repository.entities.RoomInfo;
import cz.sinko.smarthome.service.dtos.esp.RoomInfoDto;

@Service
@Transactional
public class EspServiceImpl implements EspService {

	private final RoomInfoRepository roomInfoRepository;
	private final OrikaBeanMapper mapper;

	@Autowired public EspServiceImpl(RoomInfoRepository roomInfoRepository, OrikaBeanMapper mapper) {
		this.roomInfoRepository = roomInfoRepository;
		this.mapper = mapper;
	}

	@Override public List<RoomInfoDto> getRoomInfoByDate(String sensorId, LocalDate date) {
		List<RoomInfo> roomInfoList = roomInfoRepository.findAllBySensorIdAndTimestamp(sensorId, date);
		return roomInfoList.stream().map(roomInfo -> mapper.map(roomInfo, RoomInfoDto.class)).collect(Collectors.toList());
	}

	@Override public List<RoomInfoDto> getRoomInfoFromDateTime(String sensorId, LocalDateTime date) {
		List<RoomInfo> roomInfoList = roomInfoRepository.findAllBySensorIdAndTimestampAfter(sensorId, date);
		return roomInfoList.stream().map(roomInfo -> mapper.map(roomInfo, RoomInfoDto.class)).collect(Collectors.toList());
	}

}
