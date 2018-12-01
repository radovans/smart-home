package cz.sinko.smarthome.service.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInputDto {

	@JsonProperty("sys")
	private SunriseAndSunsetDto sunriseAndSunset;

	@JsonProperty("main")
	private Temperature temperature;

	@JsonProperty("dt")
	private String timestamp;

	private String name;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class SunriseAndSunsetDto {

		private String sunrise;

		private String sunset;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Temperature {

		@JsonProperty("temp")
		private String temperature;

	}
}
