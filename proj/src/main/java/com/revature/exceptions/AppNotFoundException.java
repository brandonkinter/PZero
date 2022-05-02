package com.revature.exceptions;

public class AppNotFoundException extends Exception {
	private static final long serialVersionUID = 1901238106243156121L;
	
	public String getMessage() {
		return "No applications found!\n";
	}

}
