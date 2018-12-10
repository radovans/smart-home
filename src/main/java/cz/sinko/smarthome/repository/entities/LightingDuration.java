package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "lighting_durations")
public class LightingDuration implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private Light light;

	@Column(name = "duration_of_lighting")
	private Long durationOfLightingInSeconds;

	@NotNull
	@Column(name = "lighting_from")
	private LocalDateTime lightingFrom;

	@NotNull
	@Column(name = "lighting_to")
	private LocalDateTime lightingTo;

}
