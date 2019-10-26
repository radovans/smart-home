package cz.sinko.smarthome.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("feature")
public class FeatureToogles {

	private final Map<String, Boolean> toggles = new HashMap<>();

	public Map<String, Boolean> getToggles() {
		return toggles;
	}

	public boolean lightsInfoEnabled() {
		return toggles.getOrDefault("feature.toggle.cron.lights.info", true);
	}

	public boolean weatherInfoEnabled() {
		return toggles.getOrDefault("feature.toggle.cron.weather.info", true);
	}

	public boolean roomInfoEnabled() {
		return toggles.getOrDefault("feature.toggle.cron.room.info", true);
	}

}