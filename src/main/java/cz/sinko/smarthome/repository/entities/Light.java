package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "lights")
public class Light implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(name = "light_id", unique = true)
	private String lightId;

}
