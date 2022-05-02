package com.revature.models;

public class Admin extends Employee {
	public Admin() {
		super();
	}
	
	public Admin(int userID) {
		super(userID);
		this.setRole("admin");
	}
		
	@Override
	public String toString() {
		return "I am an admin.";
	}
}
