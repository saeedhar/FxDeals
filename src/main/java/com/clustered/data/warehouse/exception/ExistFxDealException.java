package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;

public class ExistFxDealException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ExistFxDealException(String uniqueId) {
		super("Fx-Deal with uniqueId: " + uniqueId + " already exists.", HttpStatus.CONFLICT);
	}
}
