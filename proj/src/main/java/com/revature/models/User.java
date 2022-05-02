package com.revature.models;

import java.util.Objects;

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
	
	@Override
	public int hashCode() {
		return Objects.hash(userID, userRole);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return userID == other.userID && Objects.equals(userRole, other.userRole);
	}
}
