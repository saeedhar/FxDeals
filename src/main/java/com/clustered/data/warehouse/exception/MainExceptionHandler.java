package com.clustered.data.warehouse.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<String> handleBaseExceptions(BaseException ex) {
		return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
	}
}
