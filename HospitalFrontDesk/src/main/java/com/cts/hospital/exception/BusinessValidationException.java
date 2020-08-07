package com.cts.hospital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessValidationException(String message) {
		super(message);
	}

	public BusinessValidationException(String message, Throwable t) {
		super(message, t);
	}
}
