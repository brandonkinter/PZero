package com.revature.models;

import java.util.Scanner;

import com.revature.dao.DAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.InvalidPasswordException;

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
	
	public void userProfile(Scanner scan) {
		int choice = 0;
		
		while(choice != 4) {
			this.displayProfile();
			System.out.print("What would you like to do? ");
			choice = scan.nextInt();
			
			switch(choice) {
				case 1: // update password
					this.updatePassword(scan);
					break;
				case 2: // update email
					break;
				case 3: // update phone number
					break;
				case 4: // quit
					break;
				default:
					System.out.println("Invalid choice! Try again.");
			}
		}
	}
	
	public abstract void displayOptionsMenu();
	
	public abstract void optionOne(Scanner scan);
	
	public abstract void optionTwo(Scanner scan);
	
	public abstract void optionThree(Scanner scan);
	
	public abstract void optionFour(Scanner scan);
	
	@Override
	public String toString() {
		return "userID: " + this.userID + " | userRole: " + this.userRole;
	}
	
	private void displayProfile() {
		System.out.println("\n-----USER PROFILE-----\n");
		System.out.println("name: ");
		System.out.println("username: ");
		System.out.println("email: ");
		System.out.println("phone number: \n");
		System.out.println("1. Update password.");
		System.out.println("2. Update email.");
		System.out.println("3. Update phone number.");
		System.out.println("4. Quit.");
	}
	
	private void updatePassword(Scanner scan) {
		boolean success = false;
		Login login = Login.retrieve(this.userID);
		
		while(!success) {
			System.out.print("\nChoose a new password (Note - " +
					 		 "Password must contain at least 8 " + 
					 		 "characters,\nincluding a number, a " +
					 		 "lowercase and uppercase letter, and " +
					 		 "one of the following\nspecial " +
					 		 "characters: !, ?, #, $, %, &, ^, * " +
							 ", @, +).\n\npassword: ");
			try {
				login.setPassword(scan.next());
				login.update();
				success = true;
			} catch (InvalidPasswordException e) {
				System.out.println("Invalid password! Try again.");
			} // end try-catch			
		}
		System.out.println("Password updated successfully!");
	}
}
