package cz.sinko.smarthome.service.dto.esp;

import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author radovan.sinko@direct.cz
 */
@Data
@AllArgsConstructor
public class HumidityDto {

	@ApiModelProperty(value = "HumidityDto")
	@NotNull
	private float humidity;

	@ApiModelProperty(value = "Timestamp")
	@NotNull
	private Date timestamp;
}
