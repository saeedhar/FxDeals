package com.clustered.data.warehouse.exception;


import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ExistFxDealException extends BaseException {
    public ExistFxDealException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
