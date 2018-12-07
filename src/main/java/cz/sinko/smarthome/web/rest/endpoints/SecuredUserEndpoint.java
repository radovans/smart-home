package cz.sinko.smarthome.web.rest.endpoints;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.services.UserService;

@RestController
@Validated
@RequestMapping(value = "/secured/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
		MediaType.APPLICATION_JSON_VALUE)
public class SecuredUserEndpoint {

	private final UserService userService;

	@Autowired public SecuredUserEndpoint(UserService userService) {
		this.userService = userService;
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@NotNull @PathVariable("id") Long id) {
		userService.deleteUser(id);
	}

}