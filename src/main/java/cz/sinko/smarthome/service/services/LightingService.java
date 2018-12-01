package cz.sinko.smarthome.service.services;

import cz.sinko.smarthome.service.dtos.lighting.LightDto;

public interface LightingService {

	String getInfo();

	String getLightsInfo();

	LightDto getLightById(long id);

	void changeLightByIdState(long id);
}
