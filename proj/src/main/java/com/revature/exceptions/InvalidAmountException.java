package com.revature.exceptions;

public class InvalidAmountException extends Exception {
	private static final long serialVersionUID = -1132656662362944309L;
	
	public String getMessage() {
		return "\nInvalid amount! Try again.\n";
	}

}
