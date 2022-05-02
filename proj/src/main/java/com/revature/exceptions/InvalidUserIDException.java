package com.revature.exceptions;

public class InvalidUserIDException extends Exception {
	private static final long serialVersionUID = 6076198773946099676L;
	
	public String getMessage() {
		return "Invalid User ID! Try again.\n";
	}

}
