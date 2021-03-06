package cz.sinko.smarthome.web.rest.endpoints.lighting;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.dtos.lighting.LightingInfoDto;
import cz.sinko.smarthome.service.services.LightingCalculationsService;
import cz.sinko.smarthome.service.services.LightingService;

@RestController
@Validated
@RequestMapping(value = "/lighting", produces = MediaType.APPLICATION_JSON_VALUE)
public class LightingEndpoint {

	private final LightingService lightingService;
	private final LightingCalculationsService lightingCalculationsService;

	@Autowired public LightingEndpoint(LightingService lightingService,
			LightingCalculationsService lightingCalculationsService) {
		this.lightingService = lightingService;
		this.lightingCalculationsService = lightingCalculationsService;
	}

	@GetMapping(path = "/info")
	@ResponseStatus(HttpStatus.OK)
	public String getInfo() {
		return lightingService.getInfo();
	}

	@GetMapping(path = "/lights")
	@ResponseStatus(HttpStatus.OK)
	public String getLightsInfo() {
		return lightingService.getLightsInfo();
	}

	@GetMapping(path = "/lightingInfo")
	@ResponseStatus(HttpStatus.OK)
	public LightingInfoDto getLightingInfo(
			@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		if (date != null) {
			return lightingCalculationsService.getLightingInfo(date);
		}
		return lightingCalculationsService.getLightingInfo(LocalDate.now());
	}

}
