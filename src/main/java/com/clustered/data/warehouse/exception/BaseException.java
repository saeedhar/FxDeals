package com.clustered.data.warehouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;

	public BaseException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

}
