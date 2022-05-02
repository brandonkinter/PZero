package com.revature.models;

import java.util.ArrayList;

public class Customer extends User {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		super();
		accounts = null;
	}
	
	public Customer(int userID) {
		super(userID, "customer");
	}
	
	public Customer(int userID, ArrayList<Account> accounts) {
		super(userID);
		this.setRole("customer");
		this.accounts = accounts;
	}
	
	public ArrayList<Account> getAccounts() {
		return this.accounts;
	}
	
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	@Override
	public String toString() {
		return "User ID: " + this.getID() +
			   "\n\nAccounts: "+ accounts;
	}
	
} // end Customer
