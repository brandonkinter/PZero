package com.revature.models;

import java.util.Scanner;

public class Employee extends User {
	public Employee() {
		super();
	}
	
	public Employee(int userID) {
		super(userID);
		this.setRole("employee");
	}

	public void displayOptionsMenu(Scanner scan) {
		
	}
	
	@Override
	public String toString() {
		return "I am an employee.";
	}
}
