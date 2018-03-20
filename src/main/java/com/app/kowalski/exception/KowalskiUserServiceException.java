package com.app.kowalski.exception;

@SuppressWarnings("serial")
public class KowalskiUserServiceException extends Exception {

	public KowalskiUserServiceException () {}

	public KowalskiUserServiceException(String message) {
		super(message);
	}

	public KowalskiUserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
