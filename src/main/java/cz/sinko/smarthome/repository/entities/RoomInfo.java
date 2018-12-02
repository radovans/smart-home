package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "rooms_info")
public class RoomInfo implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String sensorId;

	@NotNull
	private BigDecimal humidity;

	@NotNull
	private BigDecimal temperature;

	@NotNull
	private LocalDateTime timestamp;
}
