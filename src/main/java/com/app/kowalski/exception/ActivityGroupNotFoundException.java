package com.app.kowalski.exception;

@SuppressWarnings("serial")
public class ActivityGroupNotFoundException extends Exception {

	public ActivityGroupNotFoundException () {}

	public ActivityGroupNotFoundException(String message) {
		super(message);
	}

	public ActivityGroupNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
