package com.app.kowalski.exception;

public class TimeRecordNotFoundException extends Exception {

	public TimeRecordNotFoundException () {}

	public TimeRecordNotFoundException(String message) {
		super(message);
	}

	public TimeRecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
