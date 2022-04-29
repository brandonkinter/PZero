package com.revature.models;

import java.util.ArrayList;
import java.util.Objects;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountJunctionDAO;
import com.revature.exceptions.NotFoundException;
import com.revature.exceptions.InvalidAmountException;
import com.revature.exceptions.NotEnoughFundsException;

public class BankAccount {
	private int acctNum;
	private long balance;
	private static AccountDAO acctDAO = new AccountDAO();
	private static AccountJunctionDAO acctJuncDAO = new AccountJunctionDAO();
	
	public BankAccount() {
		this.acctNum = -1;
		this.balance = 0;
	}
	
	public BankAccount(long balance) {
		this.acctNum = -1;
		this.balance = balance;
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
	
	public void create(int userID) {
		acctJuncDAO.create(new AccountJunction(userID, acctDAO.create(this)));
	}
	
	public static BankAccount retrieve(int acctNum) 
									throws NotFoundException {
		BankAccount account = acctDAO.retrieve(acctNum);
		
		if(account == null)
			throw new NotFoundException();
		
		return account;
	}
	
	public static ArrayList<Integer> retrieveCusts(int acctNum) 
												throws NotFoundException {
		ArrayList<Integer> custs = new ArrayList<Integer>();
		ArrayList<AccountJunction> juncs = acctJuncDAO.retrieveByAcct(acctNum);
		
		if(juncs == null) {
			throw new NotFoundException();
		}
		
		for(AccountJunction i : juncs) {
			custs.add(i.getUserID());
		}
		
		return custs;
	}
	
	public void delete() {
		acctJuncDAO.delete(new AccountJunction(this.acctNum));
		acctDAO.delete(this);
	}
	
	public void deposit(long amount) throws InvalidAmountException {
		if(amount <= 0)
			throw new InvalidAmountException();
		
		this.balance += amount;
		acctDAO.update(this);
	} // end deposit()
	
	public void withdraw(long amount) throws InvalidAmountException,
											 NotEnoughFundsException {
		if(amount < 0)
			throw new InvalidAmountException();
		else if(this.balance - amount < 0)
			throw new NotEnoughFundsException();
		
		this.balance -= amount;
		acctDAO.update(this);
	}
	
	public void transfer(long amount, BankAccount dest) 
											throws InvalidAmountException, 
												   NotEnoughFundsException {
		this.withdraw(amount);
		dest.deposit(amount);
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
