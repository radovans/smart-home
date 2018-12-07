package cz.sinko.smarthome.service.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import cz.sinko.smarthome.service.dtos.esp.RoomInfoDto;

public interface EspService {

	List<RoomInfoDto> getRoomInfoByDate(String sensorId, LocalDate date);

	List<RoomInfoDto> getRoomInfoFromDateTime(String sensorId, LocalDateTime date);

}
