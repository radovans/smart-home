package cz.sinko.smarthome.service.services.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.LightDao;
import cz.sinko.smarthome.repository.entities.Light;
import cz.sinko.smarthome.service.dtos.inputs.LightInfoInputDto;
import cz.sinko.smarthome.service.dtos.inputs.NewLightsInputDto;
import cz.sinko.smarthome.web.rest.exceptions.UnknownLightException;

@Service
@Transactional
@ConditionalOnProperty(name = "feature.toggles.cron.lights.info", havingValue = "true", matchIfMissing = true)
public class NewLightsProvider {

	private static final String USERNAME = "wpLE-C3WbBRVSXnWWn4oZQAyfZWB9TEqB-vt3MUS";
	private static final Logger logger = LoggerFactory.getLogger(LightsInfoProvider.class);

	private final LightDao lightDao;

	@Autowired public NewLightsProvider(LightDao lightDao) {
		this.lightDao = lightDao;
	}

	@Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 5000)
	public void findNewLights() throws UnknownLightException {
		logger.debug("Looking for new lights");
		NewLightsInputDto newLightsInputDto = getAllLights();
		logger.debug(newLightsInputDto.toString());

		int bulbCount = newLightsInputDto.getNewLightInputDto().getTotal()
				- newLightsInputDto.getNewLightInputDto().getAvailable();

		//first light in philips api is 1
		for (int i = 1; i <= bulbCount; i++) {
			LightInfoInputDto lightInfoInputDto = getLightInfo(i);
			logger.debug(lightInfoInputDto.toString());
			if (!lightDao.findByLightId(lightInfoInputDto.getUniqueId()).isPresent()) {
				Light light = new Light();
				light.setLightId(lightInfoInputDto.getUniqueId());
				lightDao.save(light);
			}
		}
	}

	private NewLightsInputDto getAllLights() {
		final String uri = "http://192.168.0.241/api/" + USERNAME + "/capabilities";
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, NewLightsInputDto.class);
	}

	private LightInfoInputDto getLightInfo(int bulbNumber) {
		final String uri = "http://192.168.0.241/api/" + USERNAME + "/lights/" + bulbNumber;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, LightInfoInputDto.class);
	}

}