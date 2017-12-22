package com.app.kowalski.exception;

public class InvalidTimeRecordException extends Exception {

	public InvalidTimeRecordException () {}

	public InvalidTimeRecordException(String message) {
		super(message);
	}

	public InvalidTimeRecordException(String message, Throwable cause) {
		super(message, cause);
	}

}
