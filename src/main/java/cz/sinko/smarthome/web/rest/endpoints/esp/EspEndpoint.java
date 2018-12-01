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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.dtos.esp.RoomInfoDto;
import cz.sinko.smarthome.service.services.EspService;

@RestController
@Validated
@RequestMapping(value = "/esp", produces = MediaType.APPLICATION_JSON_VALUE)
public class EspEndpoint {

	@Autowired
	private EspService espService;

	@GetMapping(path = "/{sensorId}")
	@ResponseStatus(HttpStatus.OK)
	public List<RoomInfoDto> getRoomInfoByDate(@PathVariable long sensorId, @RequestParam("date") Date date, @RequestParam("from") Date fromDateTime) {
		if (date != null) {
			return espService.getRoomInfoByDate(sensorId, date);
		}
		if (fromDateTime != null) {
			return espService.getRoomInfoFromDateTime(sensorId, fromDateTime);
		}
		return espService.getRoomInfoByDate(sensorId, new Date());
	}

}