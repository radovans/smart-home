package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import cz.sinko.smarthome.repository.entities.enums.State;
import lombok.Data;

@Data
@Entity(name = "lights_states")
public class LightState implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "light_id")
	private Light light;

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

	@NotNull
	private LocalDateTime timestamp;

}
