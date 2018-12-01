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
@Entity(name = "rooms_info")
public class RoomInfo implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private long sensorId;

	@NotNull
	private float humidity;

	@NotNull
	private float temperature;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
}
