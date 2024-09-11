package org.yvynnyk.timetrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for managing exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles IllegalStateException and returns a 400 Bad Request response.
	 *
	 * @param ex the exception to handle
	 * @return a response with the exception message and a 400 Bad Request status code
	 */
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles ResourceNotFoundException and returns a 404 Not Found response.
	 *
	 * @param ex the exception to handle
	 * @return a response with the exception message and a 404 Not Found status code
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}