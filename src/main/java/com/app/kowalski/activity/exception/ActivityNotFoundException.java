package com.app.kowalski.activity.exception;

@SuppressWarnings("serial")
public class ActivityNotFoundException extends Exception {

	public ActivityNotFoundException () {}

	public ActivityNotFoundException(String message) {
		super(message);
	}

	public ActivityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
