package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MainExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<String> handleBaseExceptions(BaseException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralExceptions(Exception ex) {
		return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
