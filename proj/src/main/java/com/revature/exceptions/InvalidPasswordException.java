package com.revature.exceptions;

public class InvalidPasswordException extends Exception {
	private static final long serialVersionUID = -5754261567514472067L;

	@Override
	public String getMessage() {
		return "\nInvalid password! Try again.\n";
	}
}
