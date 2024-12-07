package com.authservice.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException(String ex) {
		super(ex);
	}
}
