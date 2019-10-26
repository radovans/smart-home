package cz.sinko.smarthome.web.rest.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.smarthome.service.dtos.LoginDto;
import cz.sinko.smarthome.service.dtos.user.UserWithTokenDto;
import cz.sinko.smarthome.service.services.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Validated
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationEndpoint {

	private final UserService userService;

	@Autowired
	public AuthenticationEndpoint(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/login")
	@ResponseStatus(HttpStatus.OK)
	public UserWithTokenDto login(@RequestBody LoginDto loginDto) {
		return userService.login(loginDto);
	}

	//TODO: Logout service

}
