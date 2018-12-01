package cz.sinko.smarthome.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "saving_settings")
public class SavingSetting implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private int normalBulbWoltage;


}

