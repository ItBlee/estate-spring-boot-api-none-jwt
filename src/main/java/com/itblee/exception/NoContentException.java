package com.itblee.exception;

public class NoContentException extends RuntimeException{

	public NoContentException() {
	}

	public NoContentException(String message) {
		super(message);
	}

	public NoContentException(Throwable cause) {
		super(cause);
	}

}
