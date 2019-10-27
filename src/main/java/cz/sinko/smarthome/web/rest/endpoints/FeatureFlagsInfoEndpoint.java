package cz.sinko.smarthome.web.rest.endpoints;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import cz.sinko.smarthome.config.FeatureFlags;

@Component
@Endpoint(id = "features")
class FeatureFlagsInfoEndpoint {

	@Autowired
	private FeatureFlags featureFlags;

	@ReadOperation
	public Map<String, Boolean> featureFlags() {
		return featureFlags.getFlags();
	}

}