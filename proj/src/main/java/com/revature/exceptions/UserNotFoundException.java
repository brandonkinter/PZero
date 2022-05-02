package com.revature.exceptions;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1689381930415180837L;
	
	public String getMessage() {
		return "\nUser not found!\n";
	}

}
