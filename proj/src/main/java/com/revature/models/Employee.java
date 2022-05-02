package com.revature.models;

public class Employee extends User {
	public Employee() {
		super();
	}
	
	public Employee(int userID) {
		super(userID);
		this.setRole("employee");
	}
}
