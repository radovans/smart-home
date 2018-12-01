package cz.sinko.smarthome.service;

import java.util.Date;
import java.util.List;

import cz.sinko.smarthome.service.dto.esp.HumidityDto;
import cz.sinko.smarthome.service.dto.esp.TemperatureDto;

/**
 * @author radovan.sinko@direct.cz
 */
public interface EspService {

	List<HumidityDto> getHumidityByDate(Date date);

	List<HumidityDto> getHumidityFrom(Date date);

	List<TemperatureDto> getTemperatureByDate(Date date);

	List<TemperatureDto> getTemperatureFrom(Date date);

}
