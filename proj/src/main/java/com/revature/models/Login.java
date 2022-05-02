package com.revature.models;

public class Login {

	private String username;
	private String password;
	private int userID;
	
	public Login() {
		this.username = "";
		this.password = "";
		this.userID = -1;
	}
	
	public Login(String username, String password) {
		this.username = username;
		this.password = password;
		this.userID = -1;
	}
	
	public Login(String username, String password, int userID) {
		this.username = username;
		this.password = password;
		this.userID = userID;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	@Override
	public String toString() {
		return "username: " + this.username + " | password: " +
			    this.password + " | userID: " + this.userID;
	}
	
} // end Login
