package cz.sinko.smarthome.service.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.repository.daos.SunInfoRepository;
import cz.sinko.smarthome.repository.entities.SunInfo;
import cz.sinko.smarthome.web.rest.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {

	private final SunInfoRepository sunInfoRepository;

	@Autowired public WeatherServiceImpl(SunInfoRepository sunInfoRepository) {
		this.sunInfoRepository = sunInfoRepository;
	}

	@Override public SunInfo getSunInfoByDate(LocalDate date) {
		return sunInfoRepository.findByDate(date).orElseThrow(ResourceNotFoundException::new);
	}

}
