package cz.sinko.smarthome.service.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDto {

	@NotNull
	@Size(min = 1)
	private String username;

	@NotNull
	@Size(min = 1)
	private String password;

}