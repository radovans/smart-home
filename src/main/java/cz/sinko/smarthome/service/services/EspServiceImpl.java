package cz.sinko.smarthome.service.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.config.mapper.OrikaBeanMapper;
import cz.sinko.smarthome.repository.daos.HumidityDao;
import cz.sinko.smarthome.repository.daos.TemperatureDao;
import cz.sinko.smarthome.repository.entities.esp.Humidity;
import cz.sinko.smarthome.repository.entities.esp.Temperature;
import cz.sinko.smarthome.service.dtos.esp.HumidityDto;
import cz.sinko.smarthome.service.dtos.esp.TemperatureDto;

/**
 * @author radovan.sinko@direct.cz
 */
@Service
@Transactional
public class EspServiceImpl implements EspService {

	@Autowired
	private HumidityDao humidityDao;

	@Autowired
	private TemperatureDao temperatureDao;

	@Autowired
	private OrikaBeanMapper mapper;

	@Override public List<HumidityDto> getHumidityByDate(Date date) {
		List<Humidity> humidities = humidityDao.findAllByTimestamp(date);
		return new ArrayList<>(humidities.stream().map(humidity -> mapper.map(humidity, HumidityDto.class)).collect(Collectors.toList()));
	}

	@Override public List<HumidityDto> getHumidityFrom(Date date) {
		List<Humidity> humidities = humidityDao.findAllByTimestampAfter(date);
		return new ArrayList<>(humidities.stream().map(humidity -> mapper.map(humidity, HumidityDto.class)).collect(Collectors.toList()));
	}

	@Override public List<TemperatureDto> getTemperatureByDate(Date date) {
		List<Temperature> temperatures = temperatureDao.findAllByTimestamp(date);
		return new ArrayList<>(temperatures.stream().map(temperature -> mapper.map(temperature, TemperatureDto.class)).collect(Collectors.toList()));
	}

	@Override public List<TemperatureDto> getTemperatureFrom(Date date) {
		List<Temperature> temperatures = temperatureDao.findAllByTimestampAfter(date);
		return new ArrayList<>(temperatures.stream().map(temperature -> mapper.map(temperature, TemperatureDto.class)).collect(Collectors.toList()));
	}
}
