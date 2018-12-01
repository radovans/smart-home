package cz.sinko.smarthome.service.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * !!! WATCHOUT !!! Each bulb must be inserted here
 * Philips Hue API is returning objects instead of list of objects
 * so we need to have separate parameters for each bulb.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LightInfoListInputDto {

	@JsonProperty("1")
	private LightInfoInputDto lightInfoInputDto1;

	@JsonProperty("2")
	private LightInfoInputDto lightInfoInputDto2;
}
