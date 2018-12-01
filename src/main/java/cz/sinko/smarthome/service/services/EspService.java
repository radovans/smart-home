package cz.sinko.smarthome.service.services;

import java.util.Date;
import java.util.List;

import cz.sinko.smarthome.service.dtos.esp.HumidityDto;
import cz.sinko.smarthome.service.dtos.esp.TemperatureDto;

/**
 * @author radovan.sinko@direct.cz
 */
public interface EspService {

	List<HumidityDto> getHumidityByDate(Date date);

	List<HumidityDto> getHumidityFrom(Date date);

	List<TemperatureDto> getTemperatureByDate(Date date);

	List<TemperatureDto> getTemperatureFrom(Date date);

}
