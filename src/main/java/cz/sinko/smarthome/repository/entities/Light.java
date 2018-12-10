package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;

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
	private String lightId;

}
