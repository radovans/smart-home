package cz.sinko.smarthome.service.dtos.lighting;

import java.math.BigDecimal;
import java.time.Duration;

import lombok.Data;

@Data
public class LightingInfoDto {

	Duration lightingDuration;
	BigDecimal powerSavings;
	Duration lightingDurationDuringDay;

}
