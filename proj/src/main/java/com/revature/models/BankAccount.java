package com.revature.models;

public class BankAccount {
	private int acctNum;
	private long balance;
	
	public BankAccount() {
		this.acctNum = -1;
		this.balance = 0;
	}
	
	public BankAccount(int acctNum, long balance) {
		this.acctNum = acctNum;
		this.balance = balance;
	}
	
	public int getAcctNum() {
		return this.acctNum;
	}
	
	public long getBalance() {
		return this.balance;
	}
	
	public void setAcctNum(int acctNum) {
		this.acctNum = acctNum;
	}
	
	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "acctNum: " + this.acctNum + 
			   " | balance: " + (double)this.balance/100.0;
	}
}
