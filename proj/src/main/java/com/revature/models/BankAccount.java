package com.revature.models;

import java.util.Objects;

import com.revature.dao.AccountDAO;
import com.revature.dao.DAO;
import com.revature.exceptions.InvalidDepositException;
import com.revature.exceptions.InvalidWithdrawalException;
import com.revature.exceptions.NotEnoughFundsException;

public class BankAccount {
	private int acctNum;
	private long balance;
	private static DAO<BankAccount, Integer, Void> accountDAO 
													= new AccountDAO();
	
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
	
	public static BankAccount retrieve(int acctNum) {
		return accountDAO.retrieve(acctNum);
	}
	
	public void deposit(long amount) throws InvalidDepositException {
		if(amount <= 0)
			throw new InvalidDepositException();
		
		this.balance += amount;
		accountDAO.update(this);
	} // end deposit()
	
	public void withdraw(long amount) throws InvalidWithdrawalException,
											 NotEnoughFundsException {
		if(amount < 0)
			throw new InvalidWithdrawalException();
		else if(this.balance - amount < 0)
			throw new NotEnoughFundsException();
		this.balance -= amount;
		accountDAO.update(this);
	}
	
	@Override
	public String toString() {
		return "acctNum: " + this.acctNum + 
			   " | balance: " + (double)this.balance/100.0;
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
		BankAccount other = (BankAccount) obj;
		return acctNum == other.acctNum && balance == other.balance;
	}
	
	
}
