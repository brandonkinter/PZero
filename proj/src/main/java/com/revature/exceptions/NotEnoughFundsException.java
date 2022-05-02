package com.revature.exceptions;

public class NotEnoughFundsException extends Exception {
	private static final long serialVersionUID = 1969054829816180695L;

	public String getMessage() {
		return "\nNot enough funds!\n";
	}
}
