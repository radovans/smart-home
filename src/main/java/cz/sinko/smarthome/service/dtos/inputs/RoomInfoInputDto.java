package cz.sinko.smarthome.service.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomInfoInputDto {

	private String temperature;

	private String humidity;

	private String sensorId;

}