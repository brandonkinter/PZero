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

	public void displayOptionsMenu() {
		System.out.println("1. Approve or deny applications.");
		System.out.println("2. Customer operations.");
		System.out.println("3. Account operations.");
		System.out.println("4. Customer records.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	public void optionTwo(Scanner scan) {
		
	}
	
	public void optionThree(Scanner scan) {
		
	}
	
	@Override
	public String toString() {
		return "I am an employee.";
	}
}
