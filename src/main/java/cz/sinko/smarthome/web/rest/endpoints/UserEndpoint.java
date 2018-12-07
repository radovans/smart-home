package cz.sinko.smarthome.web.rest.endpoints;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.dtos.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.UserDto;
import cz.sinko.smarthome.service.dtos.UserListDto;
import cz.sinko.smarthome.service.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

	private final UserService userService;

	@Autowired public UserEndpoint(UserService userService) {
		this.userService = userService;
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

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public UserListDto getAllUsers() {
		return userService.getAllUsers();
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UserDto updateUser(@NotNull @PathVariable("id") Long id,
			@NotNull @Valid @RequestBody UserCreateUpdateDto userDto) {
		return userService.updateUser(id, userDto);
	}

}
