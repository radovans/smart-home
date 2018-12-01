package cz.sinko.smarthome.service.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.config.mapper.OrikaBeanMapper;
import cz.sinko.smarthome.repository.daos.RoomInfoDao;
import cz.sinko.smarthome.repository.entities.esp.RoomInfo;
import cz.sinko.smarthome.service.dtos.esp.RoomInfoDto;

@Service
@Transactional
public class EspServiceImpl implements EspService {

	@Autowired
	private RoomInfoDao roomInfoDao;

	@Autowired
	private OrikaBeanMapper mapper;

	@Override public List<RoomInfoDto> getRoomInfoByDate(long sensorId, Date date) {
		List<RoomInfo> roomInfoList = roomInfoDao.findAllBySensorIdAndTimestamp(sensorId, date);
		return new ArrayList<>(roomInfoList.stream().map(roomInfo -> mapper.map(roomInfo, RoomInfoDto.class)).collect(Collectors.toList()));
	}

	@Override public List<RoomInfoDto> getRoomInfoFromDateTime(long sensorId, Date date) {
		List<RoomInfo> roomInfoList = roomInfoDao.findAllBySensorIdAndTimestampAfter(sensorId, date);
		return new ArrayList<>(roomInfoList.stream().map(roomInfo -> mapper.map(roomInfo, RoomInfoDto.class)).collect(Collectors.toList()));
	}

}
