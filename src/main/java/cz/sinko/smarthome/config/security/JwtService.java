package cz.sinko.smarthome.config.security;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import cz.sinko.smarthome.repository.entities.User;

@Validated
public interface JwtService {

	String generateToken(@NotNull @Valid User user);

	User parseToken(@NotNull String token);
}

