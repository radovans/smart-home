package cz.sinko.smarthome.service.services;

import cz.sinko.smarthome.service.dtos.lighting.LightDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class LightingServiceImpl implements LightingService {

	public static final String DEVICE_TYPE = "hue-api";
	public static final String USERNAME = "wpLE-C3WbBRVSXnWWn4oZQAyfZWB9TEqB-vt3MUS";

	@Override public String getInfo() {
		final String uri = "http://192.168.0.241/api/" + USERNAME;
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return result;
	}

	@Override public String getLightsInfo() {
		final String uri = "http://192.168.0.241/api/" + USERNAME + "/lights";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return result;
	}

//	@Override public LightDto getLightById(long id) {
//		final String uri = "http://192.168.0.241/api/" + USERNAME + "/lights/" + id;
//		RestTemplate restTemplate = new RestTemplate();
//		LightDto result = restTemplate.getForObject(uri, LightDto.class);
//		return result;
//	}
//
//	@Override public void changeLightByIdState(long id) {
//		final String uri = "http://192.168.0.241/api/" + USERNAME + "/lights/" + id + "/state";
//		RestTemplate restTemplate = new RestTemplate();
//		String turnOn = "{on:true}";
//		String turnOff = "{on:false}";
//		restTemplate.put(uri, turnOff);
//	}

}
