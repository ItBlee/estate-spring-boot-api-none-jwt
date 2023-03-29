package com.itblee.exception;

public class FieldRequiredException extends RuntimeException {

	public FieldRequiredException(String errorMessage) {
		super(errorMessage);
	}
	
}
