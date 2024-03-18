package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ValidatorException extends BaseException {
	public ValidatorException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
}
