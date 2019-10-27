package cz.sinko.smarthome.web.rest.endpoints;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.config.security.LoggedInPermission;
import cz.sinko.smarthome.service.dtos.user.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.user.UserDto;
import cz.sinko.smarthome.service.dtos.user.UserListDto;
import cz.sinko.smarthome.service.services.UserService;
import cz.sinko.smarthome.web.rest.exceptions.SecurityException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Validated
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

	private final UserService userService;

	@Autowired
	public UserEndpoint(UserService userService) {
		this.userService = userService;
	}

	@LoggedInPermission
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public UserListDto getAllUsers() throws SecurityException {
		log.info("Message");
		return userService.getAllUsers();
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserById(@NotNull @PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@NotNull @Valid @RequestBody UserCreateUpdateDto userDto) {
		return userService.createUser(userDto);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UserDto updateUser(@NotNull @PathVariable("id") Long id,
			@NotNull @Valid @RequestBody UserCreateUpdateDto userDto) {
		return userService.updateUser(id, userDto);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@NotNull @PathVariable("id") Long id) {
		userService.deleteUser(id);
	}

	@LoggedInPermission
	@GetMapping(path = "/securityException")
	@ResponseStatus(HttpStatus.OK)
	public void securityException() {
		throw new SecurityException("Security exception test");
	}

}
