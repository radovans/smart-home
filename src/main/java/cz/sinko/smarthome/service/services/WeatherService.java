package cz.sinko.smarthome.service.services;

import java.time.LocalDate;

import cz.sinko.smarthome.repository.entities.SunInfo;

public interface WeatherService {

	SunInfo getSunInfoByDate(LocalDate date);

}
