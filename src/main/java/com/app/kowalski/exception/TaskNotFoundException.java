package com.app.kowalski.exception;

public class TaskNotFoundException extends Exception {

	public TaskNotFoundException () {}

	public TaskNotFoundException(String message) {
		super(message);
	}

	public TaskNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
