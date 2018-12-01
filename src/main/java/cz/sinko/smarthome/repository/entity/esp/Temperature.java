package cz.sinko.smarthome.repository.entity.esp;

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
@Entity(name = "temperatures")
public class Temperature {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private float humidity;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
}
