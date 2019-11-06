package cz.sinko.smarthome.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class SecurityException extends RuntimeException {

	public SecurityException() {
	}

	public SecurityException(String message) {
		super(message);
	}

}