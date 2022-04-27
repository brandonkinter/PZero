package com.revature.models;

import java.util.Scanner;

public class Admin extends Employee {
	public Admin() {
		super();
	}
	
	public Admin(int userID) {
		super(userID);
		this.setRole("admin");
	}
	
	public void displayOptionsMenu(Scanner scan) {
		
	}
	
	@Override
	public String toString() {
		return "I am an admin.";
	}
}
