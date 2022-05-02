package com.revature.exceptions;

public class AccountNotFoundException extends Exception {
	private static final long serialVersionUID = -4847824185654911765L;

	
	public String getMessage() {
		return "Account not found! Try again.\n";
	}
}
