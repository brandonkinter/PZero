package com.revature.models;

import java.util.Objects;

public class Account {
	private int acctNum;
	private long balance;
	
	public Account() {
		this.acctNum = -1;
		this.balance = 0;
	}
	
	public Account(long balance) {
		this.acctNum = -1;
		this.balance = balance;
	}
	
	public Account(int acctNum, long balance) {
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
			   " | balance: " + String.format("%.2f", this.balance/100.0);
	}

	@Override
	public int hashCode() {
		return Objects.hash(acctNum, balance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return acctNum == other.acctNum && balance == other.balance;
	}

}
