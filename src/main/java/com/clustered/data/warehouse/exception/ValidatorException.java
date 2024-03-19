package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;

public class ValidatorException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidatorException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
}
