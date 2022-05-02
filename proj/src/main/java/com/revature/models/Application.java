package com.revature.models;

import java.util.ArrayList;

public class Application {
	private int appID;
	private ArrayList<Integer> userIDs;
	private long income;
	private long deposit;
	private boolean isOpen;
	
	public Application() {
		this.appID = -1;
		this.income = 0;
		this.deposit = 0;
		this.userIDs = null;
		this.isOpen = false;
	}
	
	public Application(int appID, long income, long deposit) {
		this.appID = appID;
		this.income = income;
		this.deposit = deposit;
		this.userIDs = new ArrayList<Integer>();
		this.isOpen = true;
	}
	
	public Application(long income, long deposit, ArrayList<Integer> userIDs) {
		this.appID = -1;
		this.income = income;
		this.deposit = deposit;
		this.userIDs = userIDs;
		this.isOpen = true;
	}
	
	public Application(int appID, ArrayList<Integer> userIDs, 
					   long income, long deposit) {
		this.appID = appID;
		this.userIDs = userIDs;
		this.income = income;
		this.deposit = deposit;
		this.isOpen = true;
	}
	
	public Application(int appID, long income, long deposit, boolean isOpen) {
		this.appID = appID;
		this.userIDs = new ArrayList<Integer>();
		this.income = income;
		this.deposit = deposit;
		this.isOpen = isOpen;
	}
	
	public Application(int appID, ArrayList<Integer> userIDs, long income, 
					   long deposit, boolean isOpen) {
		this.appID = appID;
		this.userIDs = userIDs;
		this.income = income;
		this.deposit = deposit;
		this.isOpen = isOpen;
	}
	
	public int getAppID() {
		return this.appID;
	}
	
	public long getIncome() {
		return this.income;
	}
	
	public long getDeposit() {
		return this.deposit;
	}
	
	public ArrayList<Integer> getUserIDs() {
		return this.userIDs;
	}
	
	public boolean getIsOpen() {
		return this.isOpen;
	}
	
	public void setAppID(int appID) {
		this.appID = appID;
	}
	
	public void setUserIDs(ArrayList<Integer> userIDs) {
		this.userIDs = userIDs;
	}
	
	public void setIncome(long income) {
		this.income = income;
	}
	
	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}
	
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String toString() {
		String status = isOpen ? "open\n\n" : "closed\n\n";
		
		return  "\n\nUser IDs: " + this.userIDs +
				"\n\nApplication ID: " + this.appID +
				"\n\nAnnual Income: $" + 
				String.format("%.2f", this.income/100.0) + 
				"\n\nInitial Deposit: $" + 
				String.format("%.2f", this.deposit/100.0) +
				"\n\nApplication Status: " + status;
	}
	
}
