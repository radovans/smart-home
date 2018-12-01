package cz.sinko.smarthome.repository.entity.esp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author radovan.sinko@direct.cz
 */
@Data
@AllArgsConstructor
@Entity(name = "humidities")
public class Humidity implements Serializable{

		@Id
		@GeneratedValue
		private Long id;

		@NotNull
		private float temperature;

		@NotNull
		@Temporal(TemporalType.TIMESTAMP)
		private Date timestamp;

}
