package cz.sinko.smarthome.service;

import cz.sinko.smarthome.service.dto.lighting.LightDto;

public interface LightingService {

	String getInfo();

	String getLightsInfo();

	LightDto getLightById(long id);

	void changeLightByIdState(long id);
}
