package cz.sinko.smarthome.service.services.cron;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cz.sinko.smarthome.repository.daos.LightInfoDao;
import cz.sinko.smarthome.repository.entities.LightInfo;
import cz.sinko.smarthome.repository.entities.enums.State;
import cz.sinko.smarthome.service.dtos.inputs.LightInfoListInputDto;

@Service
@Transactional
public class LightsInfoProvider {

	private static final Logger logger = LoggerFactory.getLogger(LightsInfoProvider.class);

	public static final String USERNAME = "wpLE-C3WbBRVSXnWWn4oZQAyfZWB9TEqB-vt3MUS";

	@Autowired
	private LightInfoDao lightInfoDao;

	//TODO: optimize, clean, add mappers, for cycle and check each light separately
	@Scheduled(fixedRate = 10 * 1000, initialDelay = 5000)
	public void checkLightsState() {
		logger.debug("Checking lights states");
		LightInfoListInputDto lightsInfo = getLightsInfo();
		logger.debug(lightsInfo.toString());

		State oldState = null, newState, oldReachableState = null, newReachableState;
		Date lastChange = null;
		Date now = new Date();
		Duration durationOfLighting = null;

		Optional<LightInfo> lastLightInfo =
				lightInfoDao.findFirstByLightIdOrderByTimestampDesc(lightsInfo.getLightInfoInputDto1().getUniqueId());
		if (lastLightInfo.isPresent()) {
			oldState = lastLightInfo.get().getNewState();
			oldReachableState = lastLightInfo.get().getNewReachableState();
			lastChange = lastLightInfo.get().getTimestamp();
		}

		newState = determineNewState(lightsInfo);
		newReachableState = determineNewReachableState(lightsInfo);
		durationOfLighting = computeDurationOfLighting(oldState, newState, oldReachableState, newReachableState,
				lastChange, now);
		createNewLightInfo(lightsInfo, oldState, newState, oldReachableState, newReachableState, durationOfLighting, now);
	}

	//TODO: compute properly while reachable is OFF
	private Duration computeDurationOfLighting(State oldState, State newState, State oldReachableState,
			State newReachableState, Date lastChange, Date now) {
		if (lastChange != null) {
			if (oldState.equals(State.ON)
					&& newState.equals(State.OFF)
					&& oldReachableState.equals(State.ON)
					&& newReachableState.equals(State.ON)) {
				return Duration.between(lastChange.toInstant(), now.toInstant());
			}
		}
		return null;
	}

	private State determineNewState(LightInfoListInputDto lightsInfo) {
		State newState;
		if (lightsInfo.getLightInfoInputDto1().getLightInfoStateInputDto().isOn()) {
			newState = State.ON;
		} else {
			newState = State.OFF;
		}
		return newState;
	}

	private State determineNewReachableState(LightInfoListInputDto lightsInfo) {
		State newReachableState;
		if (lightsInfo.getLightInfoInputDto1().getLightInfoStateInputDto().isReachable()) {
			newReachableState = State.ON;
		} else {
			newReachableState = State.OFF;
		}
		return newReachableState;
	}

	private void createNewLightInfo(LightInfoListInputDto lightsInfo, State oldState, State newState,
			State oldReachableState, State newReachableState, Duration durationOfLighting, Date now) {
		if (oldState != newState || oldReachableState != newReachableState) {
			LightInfo lightInfo = new LightInfo();
			lightInfo.setLightId(lightsInfo.getLightInfoInputDto1().getUniqueId());
			lightInfo.setOldState(oldState);
			lightInfo.setOldReachableState(oldReachableState);
			lightInfo.setNewState(newState);
			lightInfo.setNewReachableState(newReachableState);
			lightInfo.setDurationOfLighting(durationOfLighting);
			lightInfo.setTimestamp(now);
			lightInfoDao.save(lightInfo);
		}
	}

	private LightInfoListInputDto getLightsInfo() {
		final String uri = "http://192.168.0.241/api/" + USERNAME + "/lights";
		RestTemplate restTemplate = new RestTemplate();
		logger.debug(restTemplate.getForObject(uri, String.class));
		return restTemplate.getForObject(uri, LightInfoListInputDto.class);
	}

}