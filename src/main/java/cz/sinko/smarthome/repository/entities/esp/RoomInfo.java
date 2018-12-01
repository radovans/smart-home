package cz.sinko.smarthome.repository.entities.esp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name = "room_infos")
public class RoomInfo {

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
