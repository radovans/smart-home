package cz.sinko.smarthome.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity handleRuntimeException(RuntimeException runtimeException) {
//		log.warn("Unexpected exception", runtimeException);
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(runtimeException.getMessage());
//	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity handleSecurityException(SecurityException securityException) {
		log.warn("Security exception", securityException);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(securityException.getMessage());
	}

}
