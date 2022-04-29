package com.revature.models;

public class AppJunction {
	private int junctionID;
	private int userID;
	private int appID;
	
	public AppJunction() {
		this.junctionID = this.userID = this.appID = -1;
	}
	
	public AppJunction(int userID, int appID) {
		this.junctionID = -1;
		this.userID = userID;
		this.appID = appID;
	}
	
	public AppJunction(int junctionID, int userID, int appID) {
		this.junctionID = junctionID;
		this.userID = userID;
		this.appID = appID;
	}
	
	public int getJunctionID() {
		return this.junctionID;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public int getAppID() {
		return this.appID;
	}
	
	public void setJunctionID(int junctionID) {
		this.junctionID = junctionID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public void setAppID(int appID) {
		this.appID = appID;
	}
}
