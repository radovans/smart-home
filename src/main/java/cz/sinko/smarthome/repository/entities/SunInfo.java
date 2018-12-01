package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "sun_info")
public class SunInfo implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date sunrise;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date sunset;

	@Column(unique = true)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;

}