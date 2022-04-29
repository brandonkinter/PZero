package com.revature.models;

import java.util.Queue;

import com.revature.dao.AppJunctionDAO;
import com.revature.dao.ApplicationDAO;
import com.revature.dao.DAO;
import com.revature.exceptions.InvalidAmountException;
import com.revature.exceptions.NotFoundException;

public class Application {
	private int appID;
	private int userID;
	private long income;
	private long deposit;
	private boolean isOpen;
	private static ApplicationDAO appDAO = new ApplicationDAO();
	private static DAO<AppJunction, Integer, Void> appJuncDAO 
													= new AppJunctionDAO();
	
	public Application() {
		this.appID = -1;
		this.income = 0;
		this.deposit = 0;
		this.userID = -1;
		this.isOpen = false;
	}
	
	public Application(int appID, long income, long deposit) {
		this.appID = appID;
		this.income = income;
		this.deposit = deposit;
		this.userID = -1;
		this.isOpen = true;
	}
	
	public Application(int appID, int userID, long income, long deposit) {
		this.appID = appID;
		this.userID = userID;
		this.income = income;
		this.deposit = deposit;
		this.isOpen = true;
	}
	
	public Application(int appID, long income, long deposit, boolean isOpen) {
		this.appID = appID;
		this.userID = -1;
		this.income = income;
		this.deposit = deposit;
		this.isOpen = isOpen;
	}
	
	public Application(int appID, int userID, long income, 
					   long deposit, boolean isOpen) {
		this.appID = appID;
		this.userID = userID;
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
	
	public int getUserID() {
		return this.userID;
	}
	
	public boolean getIsOpen() {
		return this.isOpen;
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
	
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public void create(int userID) {
		appJuncDAO.create(new AppJunction(userID, appDAO.create(this)));
	}
	
	public static Application retrieve(int appID) throws NotFoundException {
		Application app = appDAO.retrieve(appID);
		
		if(app == null)
			throw new NotFoundException();
		
		return app;
	}
	
	public static Queue<Application> retrieveByCust(int userID) 
												throws NotFoundException {
		Queue<Application> apps = appDAO.retrieveByCust(userID);
		
		if(apps == null)
			throw new NotFoundException();
		
		return apps;
			
	}
	
	public static Queue<Application> retrieveAll() {
		return appDAO.retrieveAll();
	}
	
	public static Queue<Application> retrieveAllOpen() {
		return appDAO.retrieveAllOpen();
	}
	
	public static Queue<Application> retrieveAllClosed() {
		return appDAO.retrieveAllClosed();
	}
	
	public void close() {
		appDAO.close(this);
	}
	
	public void delete() {
		appJuncDAO.delete(appJuncDAO.retrieve(this.appID));
		appDAO.delete(this);
	}
	
	public String toString() {
		String status = isOpen ? "open\n\n" : "closed\n\n";
		
		return  "\n\nUser ID: " + this.userID +
				"\n\nApplication ID: " + this.appID +
				"\n\nAnnual Income: " + this.income + 
				"\n\nInitial Deposit: " + this.deposit +
				"\n\nApplication Status: " + status;
	}
	
}
