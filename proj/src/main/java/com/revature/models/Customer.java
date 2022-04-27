package com.revature.models;

import java.util.Scanner;

public class Customer extends User {
	public Customer() {
		super();
	}
	
	public Customer(int userID) {
		super(userID);
		this.setRole("customer");
	}
	
	public void create() {
		userDAO.create(this);
	}
	
	public void displayOptionsMenu(Scanner scan) {
		
	}
	@Override
	public String toString() {
		return "I am a customer.";
	}
}
