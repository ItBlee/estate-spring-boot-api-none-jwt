package com.itblee.exception;

public class ErrorRepositoryException extends RuntimeException{

	public ErrorRepositoryException() {
	}

	public ErrorRepositoryException(String message) {
		super(message);
	}

	public ErrorRepositoryException(Throwable cause) {
		super(cause);
	}

	public ErrorRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

}
