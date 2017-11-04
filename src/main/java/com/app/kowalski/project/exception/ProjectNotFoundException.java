package com.app.kowalski.project.exception;

@SuppressWarnings("serial")
public class ProjectNotFoundException extends Exception {

	public ProjectNotFoundException () {}

	public ProjectNotFoundException(String message) {
		super(message);
	}

	public ProjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
