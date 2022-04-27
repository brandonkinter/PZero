package com.revature.models;

public class Junction {
	private int junctionID;
	private int userID;
	private int acctNum;
	
	public Junction() {
		this.junctionID = this.userID = this.acctNum = -1;
	}
	
	public Junction(int userID, int acctNum) {
		this.junctionID = -1;
		this.userID = userID;
		this.acctNum = acctNum;
	}
	
	public Junction(int junctionID, int userID, int acctNum) {
		this.junctionID = junctionID;
		this.userID = userID;
		this.acctNum = acctNum;
	}
	
	public int getJunctionID() {
		return this.junctionID;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public int getAcctNum() {
		return this.acctNum;
	}
	
	public void setJunctionID(int junctionID) {
		this.junctionID = junctionID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public void setAcctNum(int acctNum) {
		this.acctNum = acctNum;
	}
	
	public String toString() {
		return "junctionID: " + this.junctionID + " | userID: " +
				this.userID + " | acctNum: " + this.acctNum;
	}
}
