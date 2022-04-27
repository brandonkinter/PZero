package com.revature.models;

import java.util.Scanner;

import com.revature.dao.DAO;
import com.revature.dao.UserDAO;

public abstract class User {
	private int userID;
	private String userRole;
	protected static DAO<User, Integer, Void> userDAO = new UserDAO();
	
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
	
	public static User retrieve(int userID) {
		return userDAO.retrieve(userID);
	}
	
	public abstract void displayOptionsMenu();
	
	public abstract void optionTwo(Scanner scan);
	
	public abstract void optionThree(Scanner scan);
	
	@Override
	public String toString() {
		return "userID: " + this.userID + " | userRole: " + this.userRole;
	}
}
