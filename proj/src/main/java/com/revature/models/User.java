package com.revature.models;

public abstract class User {
	private int userID;
	private String userRole;
	
	public User() {
		this.userID = -1;
		this.userRole = "";
	}
	
	public User(int userID) {
		this.userID = userID;
		this.userRole = "";
	}
	
	public User(int userID, String userRole) {
		this.userID = userID;
		this.userRole = userRole;
	}
	
	public int getID() {
		return this.userID;
	}
	
	public String getRole() {
		return this.userRole;
	}
	
	public void setID(int userID) {
		this.userID = userID;
	}
	
	public void setRole(String userRole) {
		this.userRole = userRole;
	}
	
	@Override
	public String toString() {
		return "userID: " + this.userID + " | userRole: " + this.userRole;
	}
}
