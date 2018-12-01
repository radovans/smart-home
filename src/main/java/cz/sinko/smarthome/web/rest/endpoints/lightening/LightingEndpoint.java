package cz.sinko.smarthome.web.rest.endpoints.lightening;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.services.LightingService;
import cz.sinko.smarthome.service.dtos.lighting.LightDto;

@RestController
@Validated
@RequestMapping(value = "/lightening", produces = MediaType.APPLICATION_JSON_VALUE)
public class LightingEndpoint {

	@Autowired
	private LightingService lightingService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public String getInfo() {
		return lightingService.getInfo();
	}

	@GetMapping(path = "/lights")
	@ResponseStatus(HttpStatus.OK)
	public String getLightsInfo() {
		return lightingService.getLightsInfo();
	}

	@GetMapping(path = "/lights/{id}")
	@ResponseStatus(HttpStatus.OK)
	public LightDto getLightById(@NotNull @PathVariable("id") long id) {
		return lightingService.getLightById(id);
	}

	@PutMapping(path = "/lights/{id}/state")
	@ResponseStatus(HttpStatus.OK)
	public void changeLightByIdState(@NotNull @PathVariable("id") long id) {
		lightingService.changeLightByIdState(id);
	}

}
