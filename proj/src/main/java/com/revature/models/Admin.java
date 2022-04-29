package com.revature.models;

import java.util.Scanner;

import com.revature.exceptions.NotFoundException;

public class Admin extends Employee {
	public Admin() {
		super();
	}
	
	public Admin(int userID) {
		super(userID);
		this.setRole("admin");
	}
	
	public void displayOptionsMenu() {
		System.out.println("1. Approve or deny applications.");
		System.out.println("2. Search.");
		System.out.println("3. Make a transaction.");
		System.out.println("4. Cancel an account.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	@Override
	public void optionTwo(Scanner scan) {
		int choice = 0;
		
		while(choice != 5) {
			displaySearchMenu();
			System.out.print("What would you like to do? ");
			choice = scan.nextInt();
			
			switch(choice) {
				case 1:
					super.applicationSearch(scan);
					break;
				case 2:
					super.customerSearch(scan);
					break;
				case 3:
					super.accountSearch(scan);
					break;
				case 4:
					this.employeeSearch(scan);
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid choice! Try again.\n");
			}
		}
	}
	
	private void displaySearchMenu() {
		System.out.println("1. Search for applications.");
		System.out.println("2. Search for customers.");
		System.out.println("3. Search for accounts.");
		System.out.println("4. Search for employees.");
		System.out.println("5. Quit.");
	}
	
	private void employeeSearch(Scanner scan) {
		System.out.print("Enter a user ID: ");
		
		try {
			System.out.println(Employee.retrieve(scan.nextInt()));
		} catch(NotFoundException e) {
			System.out.println("Employee not found!");
		}
	}
	
	@Override
	public String toString() {
		return "I am an admin.";
	}
}
