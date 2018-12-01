package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "weather_info")
public class WeatherInfo implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private float temperature;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date sunrise;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date sunset;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
}

