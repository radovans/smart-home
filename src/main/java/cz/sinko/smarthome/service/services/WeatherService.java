package cz.sinko.smarthome.service.services;

import java.util.Date;

import cz.sinko.smarthome.repository.entities.SunInfo;

public interface WeatherService {

	SunInfo getSunInfoByDate(Date date);

}
