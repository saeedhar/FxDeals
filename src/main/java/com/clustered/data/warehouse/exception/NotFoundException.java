package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String uniqueId) {
		super("Fx-Deal with uniqueId: " + uniqueId + " not found.", HttpStatus.NOT_FOUND);
	}
}
