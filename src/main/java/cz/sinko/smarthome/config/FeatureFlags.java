package cz.sinko.smarthome.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("feature")
public class FeatureFlags {

	private final Map<String, Boolean> flags = new HashMap<>();

	public Map<String, Boolean> getFlags() {
		return flags;
	}

	public boolean lightsInfoEnabled() {
		return flags.getOrDefault("feature.toggle.cron.lights.info", true);
	}

	public boolean weatherInfoEnabled() {
		return flags.getOrDefault("feature.toggle.cron.weather.info", true);
	}

	public boolean roomInfoEnabled() {
		return flags.getOrDefault("feature.toggle.cron.room.info", true);
	}

}