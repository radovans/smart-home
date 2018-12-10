package cz.sinko.smarthome.service.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewLightInputDto {

	private int available;

	private int total;

}