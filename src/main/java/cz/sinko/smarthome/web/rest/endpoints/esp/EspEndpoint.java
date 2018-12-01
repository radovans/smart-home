package cz.sinko.smarthome.web.rest.endpoints.esp;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.services.EspService;
import cz.sinko.smarthome.service.dtos.esp.HumidityDto;
import cz.sinko.smarthome.service.dtos.esp.TemperatureDto;

/**
 * @author radovan.sinko@direct.cz
 */
@RestController
@Validated
@RequestMapping(value = "/esp", produces = MediaType.APPLICATION_JSON_VALUE)
public class EspEndpoint {

	@Autowired
	private EspService espService;

	@GetMapping(path = "/humidity/{date}")
	@ResponseStatus(HttpStatus.OK)
	public List<HumidityDto> getHumidityByDate(@NotNull @PathVariable("date") Date date) {
		return espService.getHumidityByDate(date);
	}

	@GetMapping(path = "/humidity/{date}")
	@ResponseStatus(HttpStatus.OK)
	public List<HumidityDto> getHumidityFrom(@NotNull @PathVariable("date") Date date) {
		return espService.getHumidityFrom(date);
	}

	@GetMapping(path = "/temperature/{date}")
	@ResponseStatus(HttpStatus.OK)
	public List<TemperatureDto> getTemperatureByDate(@NotNull @PathVariable("date") Date date) {
		return espService.getTemperatureByDate(date);
	}

	@GetMapping(path = "/temperature/{date}")
	@ResponseStatus(HttpStatus.OK)
	public List<TemperatureDto> getTemperatureFrom(@NotNull @PathVariable("date") Date date) {
		return espService.getTemperatureFrom(date);
	}

}