package cz.sinko.smarthome.service.dtos.esp;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomInfoDto {

	@ApiModelProperty(value = "Humidity")
	@NotNull
	private float humidity;

	@ApiModelProperty(value = "Temperature")
	@NotNull
	private float temperature;

	@ApiModelProperty(value = "Timestamp")
	@NotNull
	private LocalDateTime timestamp;
}
