package cz.sinko.smarthome.service.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.repository.daos.SunInfoDao;
import cz.sinko.smarthome.repository.entities.SunInfo;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {

	private final SunInfoDao sunInfoDao;

	@Autowired public WeatherServiceImpl(SunInfoDao sunInfoDao) {
		this.sunInfoDao = sunInfoDao;
	}

	@Override public SunInfo getSunInfoByDate(LocalDate date) {
		return sunInfoDao.findByDate(date);
	}

}
