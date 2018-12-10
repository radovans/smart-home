package cz.sinko.smarthome.service.services.cron;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.LightDao;
import cz.sinko.smarthome.repository.daos.LightStateDao;
import cz.sinko.smarthome.repository.daos.LightingDurationDao;
import cz.sinko.smarthome.repository.entities.Light;
import cz.sinko.smarthome.repository.entities.LightState;
import cz.sinko.smarthome.repository.entities.LightingDuration;
import cz.sinko.smarthome.repository.entities.enums.State;
import cz.sinko.smarthome.service.dtos.inputs.LightInfoInputDto;
import cz.sinko.smarthome.web.rest.exceptions.UnknownLightException;

@Service
@Transactional
@ConditionalOnProperty(name = "feature.toggles.cron.lights.info", havingValue = "true", matchIfMissing = true)
public class LightsInfoProvider {

	private static final String USERNAME = "wpLE-C3WbBRVSXnWWn4oZQAyfZWB9TEqB-vt3MUS";
	private static final Logger logger = LoggerFactory.getLogger(LightsInfoProvider.class);

	private final LightStateDao lightStateDao;
	private final LightDao lightDao;
	private final LightingDurationDao lightingDurationDao;

	@Autowired public LightsInfoProvider(LightStateDao lightStateDao, LightDao lightDao,
			LightingDurationDao lightingDurationDao) {
		this.lightStateDao = lightStateDao;
		this.lightDao = lightDao;
		this.lightingDurationDao = lightingDurationDao;
	}

	//TODO: optimize, clean
	@Scheduled(fixedRate = 10 * 1000, initialDelay = 10000)
	public void checkLightsState() throws UnknownLightException {
		logger.info("Checking lights states");
		int lightCount = lightDao.findAll().size();

		State oldState = null, newState, oldReachableState = null, newReachableState;
		LocalDateTime lastChange = null;
		LocalDateTime now = LocalDateTime.now().withNano(0);
		Duration durationOfLighting;

		for (int i = 1; i <= lightCount; i++) {
			LightInfoInputDto lightInfoInputDto = getLightInfo(i);
			Optional<Light> optionalLight = lightDao.findByLightId(lightInfoInputDto.getUniqueId());
			if (optionalLight.isPresent()) {
				Light light = optionalLight.get();
				Optional<LightState> lastLightInfo =
						lightStateDao.findFirstByLightOrderByTimestampDesc(light);
				if (lastLightInfo.isPresent()) {
					oldState = lastLightInfo.get().getNewState();
					oldReachableState = lastLightInfo.get().getNewReachableState();
					lastChange = lastLightInfo.get().getTimestamp();
				}

				newState = determineNewState(lightInfoInputDto);
				newReachableState = determineNewReachableState(lightInfoInputDto);
				durationOfLighting = computeDurationOfLighting(oldState, newState, oldReachableState,
						newReachableState,
						lastChange, now);
				createNewLightInfo(light, oldState, newState, oldReachableState, newReachableState,
						now);
				createNewLightDuration(light, lastChange, now, durationOfLighting);
			} else {
				throw new UnknownLightException("Unknown light" + lightInfoInputDto.toString());
			}
		}
	}

	private Duration computeDurationOfLighting(State oldState, State newState, State oldReachableState,
			State newReachableState, LocalDateTime lastChange, LocalDateTime now) {
		if (lastChange != null) {
			if ((oldState.equals(State.ON)
					&& newState.equals(State.OFF)
					&& oldReachableState.equals(State.ON)
					&& newReachableState.equals(State.ON)) ||
					(oldState.equals(State.ON)
							&& newState.equals(State.ON)
							&& oldReachableState.equals(State.ON)
							&& newReachableState.equals(State.OFF))) {
				return Duration.between(lastChange, now);
			}
		}
		return null;
	}

	private State determineNewState(LightInfoInputDto lightInfoInputDto) {
		return lightInfoInputDto.getLightInfoStateInputDto().isOn() ? State.ON : State.OFF;
	}

	private State determineNewReachableState(LightInfoInputDto lightInfoInputDto) {
		return lightInfoInputDto.getLightInfoStateInputDto().isReachable() ? State.ON : State.OFF;
	}

	private void createNewLightInfo(Light light, State oldState, State newState,
			State oldReachableState, State newReachableState, LocalDateTime now) {
		if (oldState != newState || oldReachableState != newReachableState) {
			LightState lightState = new LightState();
			lightState.setLight(light);
			lightState.setOldState(oldState);
			lightState.setOldReachableState(oldReachableState);
			lightState.setNewState(newState);
			lightState.setNewReachableState(newReachableState);
			lightState.setTimestamp(now);
			lightStateDao.save(lightState);
		}
	}

	private void createNewLightDuration(Light light, LocalDateTime lastChange, LocalDateTime now,
			Duration durationOfLighting) {
		if (durationOfLighting != null) {
			LightingDuration lightingDuration = new LightingDuration();
			lightingDuration.setLight(light);
			lightingDuration.setDurationOfLightingInSeconds(durationOfLighting.getSeconds());
			lightingDuration.setLightingFrom(lastChange);
			lightingDuration.setLightingTo(now);
			lightingDurationDao.save(lightingDuration);
		}
	}

	private LightInfoInputDto getLightInfo(int bulbNumber) {
		final String uri = "http://192.168.0.241/api/" + USERNAME + "/lights/" + bulbNumber;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, LightInfoInputDto.class);
	}

}