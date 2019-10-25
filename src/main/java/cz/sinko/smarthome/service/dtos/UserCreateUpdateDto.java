package cz.sinko.smarthome.service.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cz.sinko.smarthome.repository.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateUpdateDto implements Serializable {

	@NotBlank(message = "Username should not be blank")
	@Size(min = 4, max = 30, message = "Username '${validatedValue}' must be between {min} and {max} characters long")
	private String username;

	@NotBlank(message = "Password should not be blank")
	@Size(min = 4, max = 30, message = "Password '${validatedValue}' must be between {min} and {max} characters long")
	private String password;

	private Role role;

}
