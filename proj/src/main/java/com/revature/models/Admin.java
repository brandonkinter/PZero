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
		System.out.println("2. Customer operations.");
		System.out.println("3. Account operations.");
		System.out.println("4. Employee operations.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	@Override
	public String toString() {
		return "I am an admin.";
	}
}
