package cz.sinko.smarthome.service.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.repository.daos.SunInfoDao;
import cz.sinko.smarthome.repository.entities.SunInfo;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private SunInfoDao sunInfoDao;

	@Override public SunInfo getSunInfoByDate(Date date) {
		return sunInfoDao.findByDate(date);
	}

}
