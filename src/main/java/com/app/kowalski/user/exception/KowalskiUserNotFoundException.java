package com.app.kowalski.user.exception;

public class KowalskiUserNotFoundException extends Exception {

	public KowalskiUserNotFoundException () {}

	public KowalskiUserNotFoundException(String message) {
		super(message);
	}

	public KowalskiUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
