package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class NotFoundException extends BaseException {

	public NotFoundException(String uniqueId) {
		super("Fx-Deal with unique id : " + uniqueId + " ,not found", HttpStatus.NOT_FOUND);
	}
}
