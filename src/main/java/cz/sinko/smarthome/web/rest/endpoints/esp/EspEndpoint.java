package cz.sinko.smarthome.web.rest.endpoints.esp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.dtos.esp.RoomInfoDto;
import cz.sinko.smarthome.service.services.EspService;

@RestController
@Validated
@RequestMapping(value = "/esp", produces = MediaType.APPLICATION_JSON_VALUE)
public class EspEndpoint {

	private final EspService espService;

	@Autowired public EspEndpoint(EspService espService) {
		this.espService = espService;
	}

	@GetMapping(path = "/{sensorId}")
	@ResponseStatus(HttpStatus.OK)
	public List<RoomInfoDto> getRoomInfoByDate(@PathVariable String sensorId, @RequestParam("date") LocalDate date,
			@RequestParam("from") LocalDateTime fromDateTime) {
		if (date != null) {
			return espService.getRoomInfoByDate(sensorId, date);
		}
		if (fromDateTime != null) {
			return espService.getRoomInfoFromDateTime(sensorId, fromDateTime);
		}
		return espService.getRoomInfoByDate(sensorId, LocalDate.now());
	}

}