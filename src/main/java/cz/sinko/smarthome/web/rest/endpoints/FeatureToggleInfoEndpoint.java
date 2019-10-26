package cz.sinko.smarthome.web.rest.endpoints;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import cz.sinko.smarthome.config.FeatureToogles;

@Component
@Endpoint(id = "feature-toggles")
class FeatureToggleInfoEndpoint {

	private final FeatureToogles featureToogles;

	FeatureToggleInfoEndpoint(FeatureToogles featureToogles) {
		this.featureToogles = featureToogles;
	}

	@ReadOperation
	public Map<String, Boolean> featureToggles() {
		return featureToogles.getToggles();
	}

}