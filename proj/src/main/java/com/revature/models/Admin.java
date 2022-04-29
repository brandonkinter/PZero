package com.revature.models;

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
		System.out.println("2. Search for applications.");
		System.out.println("3. Search for users.");
		System.out.println("4. Search for accounts.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	@Override
	public String toString() {
		return "I am an admin.";
	}
}
