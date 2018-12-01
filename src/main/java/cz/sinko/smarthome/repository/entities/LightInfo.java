package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import cz.sinko.smarthome.repository.entities.enums.State;
import lombok.Data;

//TODO: split light info and light bulb, save bulb power consuption for proper saving calculations
@Data
@Entity(name = "lights_info")
public class LightInfo implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String lightId;

	@Column(name = "old_state")
	@Enumerated(EnumType.STRING)
	private State oldState;

	@NotNull
	@Column(name = "new_state")
	@Enumerated(EnumType.STRING)
	private State newState;

	@Column(name = "old_reachable_state")
	@Enumerated(EnumType.STRING)
	private State oldReachableState;

	@NotNull
	@Column(name = "new_reachable_state")
	@Enumerated(EnumType.STRING)
	private State newReachableState;

	@Column(name = "duration_of_lighting")
	private Duration durationOfLighting;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

}
