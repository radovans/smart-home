package cz.sinko.smarthome.service.services;

import java.util.Date;
import java.util.List;

import cz.sinko.smarthome.service.dtos.esp.RoomInfoDto;

public interface EspService {

	List<RoomInfoDto> getRoomInfoByDate(long sensorId, Date date);

	List<RoomInfoDto> getRoomInfoFromDateTime(long sensorId, Date date);

}
