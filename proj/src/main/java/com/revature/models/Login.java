package com.revature.models;

import com.revature.dao.LoginDAO;
import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.InvalidUsernameException;
import com.revature.exceptions.UsernameTakenException;

public class Login {

	private String username;
	private String password;
	private int userID;
	private static LoginDAO loginDAO = new LoginDAO();
	
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
	
	public void setUsername(String username) throws InvalidUsernameException, 
													UsernameTakenException {
		this.checkUsername(username);
		this.username = username;
	}
	
	public void setPassword(String password) throws InvalidPasswordException {
		this.checkPassword(password);
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
	
	// pre-condition: this has valid username/password combo
	public int create() {
		return loginDAO.create(this);
	}
	
	public void update() {
		loginDAO.update(this);
	}
	// return corresponding userID if valid
	// else throws exception
	public int validate() throws InvalidLoginException {
		Integer userID = loginDAO.validate(this);
		
		if(userID == null) 
			throw new InvalidLoginException();
		
		return userID;
	} // end validate()
	
	public static Login retrieve(int userID) {
		return loginDAO.retrieve(userID);
	} // end retrieve()
	
	public static Login retrieve(String username) {
		return loginDAO.retrieve(username);
	} // end retrieve()
	
	// utility method
	// checks usernames for proper format and for uniqueness
	private void checkUsername(String username) 
							throws InvalidUsernameException,
								   UsernameTakenException {
		if(username.length() < 6)
			throw new InvalidUsernameException();
		else if(loginDAO.retrieve(username) != null)
			throw new UsernameTakenException();
	}
	
	// utility method
	// checks passwords for proper format
	public void checkPassword(String password) 
							throws InvalidPasswordException {
		// strings to check against
		String special = "!?#$%&^*@+",
			   nums = "0123456789",
			   letters = "abcdefghijklmnopqrstuvwxyz",
			   caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				
		// initialize necessary containers to false
		boolean hasSpecial, hasNum, hasLetter, hasCap;
		hasSpecial = hasNum = hasLetter = hasCap = false;
				
		int length = password.length();
				
		// if password too short
		if(length < 8) 
			throw new InvalidPasswordException();
				
		// check password for necessary characters
		for(int i = 0; i < length; ++i) {
			char ch = password.charAt(i);
					
			if(special.indexOf(ch) > -1) // if password contains special char
				hasSpecial = true;
			else if(nums.indexOf(ch) > -1) // if password contains number
				hasNum = true;
			else if(letters.indexOf(ch) > -1) // if password contains letter
				hasLetter = true;
			else if(caps.indexOf(ch) > -1) // if password contains capital
				hasCap = true;
		} // end for
				
		// if password is missing a necessary character
		if(!(hasSpecial && hasNum && hasLetter && hasCap))
			throw new InvalidPasswordException();
	} // end checkPassword()
	
} // end Login
