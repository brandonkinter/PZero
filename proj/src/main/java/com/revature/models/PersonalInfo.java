package com.revature.models;

public class PersonalInfo {
	private int userID;
	private String firstName;
	private String lastName;
	private long phoneNum;
	
	public PersonalInfo() {
		this.userID = -1;
		this.firstName = "";
		this.lastName = "";
		this.phoneNum = -1;
	}
	
	public PersonalInfo(int userID, String firstName,
					    String lastName, long phoneNum) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
	}
	
	
	public int getUserID() {
		return this.userID;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public long getPhoneNum() {
		return this.phoneNum;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setPhoneNume(long phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	@Override
	public String toString() {
		return "User ID: " + this.userID + "\n" +
			   "Name: " + this.lastName + ", " + this.firstName + "\n" +
			   "Phone Number: " + this.phoneNum;
			   
	}
}
