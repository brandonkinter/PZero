package com.revature.exceptions;

public class InvalidPhoneNumException extends Exception {
	private static final long serialVersionUID = -4687440947643020102L;
	
	public String getMessage() {
		return "\nInvalid phone number! Try again.\n";
	}

}
