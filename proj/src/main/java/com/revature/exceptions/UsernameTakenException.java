package com.revature.exceptions;

public class UsernameTakenException extends Exception {
	private static final long serialVersionUID = -7754352060035250037L;

	public String getMessage() {
		return "Username already taken! Try again.\n";
	}
}
