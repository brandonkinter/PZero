package com.revature.exceptions;

public class InvalidLoginException extends Exception {
	private static final long serialVersionUID = -7631937422769364670L;

	public String getMessage() {
		return "Invalid username or password! Try again.\n";
	}
}
