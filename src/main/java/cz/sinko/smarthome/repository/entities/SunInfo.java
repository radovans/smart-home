package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "sun_info")
public class SunInfo implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private LocalTime sunrise;

	@NotNull
	private LocalTime sunset;

	@NotNull
	@Column(unique = true)
	private LocalDate date;

}