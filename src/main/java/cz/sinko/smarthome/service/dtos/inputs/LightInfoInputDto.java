package cz.sinko.smarthome.service.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LightInfoInputDto {

	@JsonProperty("state")
	private LightInfoStateInputDto lightInfoStateInputDto;

	private String type;

	private String name;

	@JsonProperty("uniqueid")
	private String uniqueId;

}
