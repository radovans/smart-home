package cz.sinko.smarthome.service.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewLightsInputDto {

	@JsonProperty("lights")
	private NewLightInputDto newLightInputDto;

}
