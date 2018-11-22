package cz.sinko.smarthome.service;

public interface LightingService {

	String getInfo();

	String getLightsInfo();

	String getLightById(long id);

	void changeLightByIdState(long id);
}
