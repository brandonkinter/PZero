package com.revature.models;

import java.util.Queue;

import com.revature.dao.AppJunctionDAO;
import com.revature.dao.ApplicationDAO;
import com.revature.dao.DAO;
import com.revature.exceptions.InvalidAmountException;

public class Application {
	private int appID;
	private int userID;
	private long income;
	private long deposit;
	private static ApplicationDAO appDAO = new ApplicationDAO();
	private static DAO<AppJunction, Integer, Void> appJuncDAO = new AppJunctionDAO();
	
	public Application() {
		this.appID = -1;
		this.income = 0;
		this.deposit = 0;
		this.userID = -1;
	}
	
	public Application(int appID, long income, long deposit) {
		this.appID = appID;
		this.income = income;
		this.deposit = deposit;
		this.userID = -1;
	}
	
	public Application(int appID, int userID, long income, long deposit) {
		this.appID = appID;
		this.userID = userID;
		this.income = income;
		this.deposit = deposit;
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
	
	public int getUserID() {
		return this.userID;
	}
	
	public void setAppID(int appID) {
		this.appID = appID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public void setIncome(long income) throws InvalidAmountException {
		if(income < 0)
			throw new InvalidAmountException();
		
		this.income = income;
	}
	
	public void setDeposit(long deposit) throws InvalidAmountException {
		if(deposit < 10000)
			throw new InvalidAmountException();
		
		this.deposit = deposit;
	}
	
	public void create(int userID) {
		appJuncDAO.create(new AppJunction(userID, appDAO.create(this)));
	}
	
	public static Queue<Application> retrieveAll() {
		return appDAO.retrieveAll();
	}
	
	public void delete() {
		appJuncDAO.delete(appJuncDAO.retrieve(this.appID));
		appDAO.delete(this);
	}
	
	public String toString() {
		return  "\n\nUser ID: " + this.userID +
				"\n\nApplication ID: " + this.appID +
				"\n\nAnnual Income: " + this.income + 
				"\n\nInitial Deposit: " + this.deposit + "\n\n";
	}
	
}
