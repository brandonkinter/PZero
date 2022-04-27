package com.revature.models;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.exceptions.InvalidDepositException;

public class Customer extends User {
	
	private ArrayList<BankAccount> accounts;
	
	public Customer() {
		super();
		accounts = null;
	}
	
	public Customer(int userID) {
		super(userID);
		this.setRole("customer");
	}
	
	public Customer(int userID, ArrayList<BankAccount> accounts) {
		super(userID);
		this.setRole("customer");
		this.accounts = accounts;
		
	}
	
	public ArrayList<BankAccount> getAccounts() {
		return this.accounts;
	}
	
	public void create() {
		userDAO.create(this);
	}
	
	public void displayOptionsMenu() {
		displayAccounts();
		
		System.out.println("\n");
		System.out.println("1. Apply for a new account.");
		System.out.println("2. Deposit to an account.");
		System.out.println("3. Withdraw from an account.");
		System.out.println("4. Transfer between accounts.");
		System.out.println("5. Settings.");
		System.out.println("6. Log Out.");
	} // end displayOptionsMenu()
	
	
	// deposit
	public void optionTwo(Scanner scan) {
		this.deposit(scan);
	} // end optionTwo()
	
	@Override
	public String toString() {
		return "I am a customer.\n" + accounts;
	}
	
	private void displayAccounts() {
		if(accounts.isEmpty()) {
			System.out.println("\nYou don't have any accounts with us yet! " +
							   "Press '1' below to apply for an account!");
		} else {
			System.out.println("\n---  Account  ------  Balance  ---\n");
			for(BankAccount i : accounts) 
				System.out.println
							("        " + i.getAcctNum() + "            $" +
							 String.format("%.2f", i.getBalance()/100.0));
		} // end if-else
	}
	
	private void deposit(Scanner scan) {
		boolean success = false;
		
		while(!success) {
			System.out.println("Which account would you like " + 
							   "to deposit into?");
			displayAccounts();
			System.out.print("Enter account number: ");
			try {
				BankAccount b = accounts.get
									(accounts.indexOf
											(BankAccount.retrieve
													(scan.nextInt())));
				while(!success) {
					System.out.print("How much would you like to deposit? ");
					long amount = (long)(scan.nextDouble() * 100);
					try {
						b.deposit(amount);
						success = true;
					} catch (InvalidDepositException e) {
						System.out.println("Invalid deposit! Try again.");
					} // end try-catch
				} // end while
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid account! Try again.");
			} // end try-catch
		} // end while
		
	} // end deposit()
	
} // end Customer
