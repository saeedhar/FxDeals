package com.clustered.data.warehouse.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ExistFxDealException extends BaseException {
	public ExistFxDealException(String uniqeId) {
		super("Fx-Deal with uniqueId : " + uniqeId + " , already exists.", HttpStatus.CONFLICT);
	}
}
