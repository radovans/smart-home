package cz.sinko.smarthome.web.rest.exceptions;

public class UnknownLightException extends RuntimeException {

	public UnknownLightException() {
	}

	public UnknownLightException(String message) {
		super(message);
	}

}