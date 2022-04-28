package com.revature.models;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InvalidAmountException;
import com.revature.exceptions.NotEnoughFundsException;

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
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	} // end displayOptionsMenu()
	
	public void optionTwo(Scanner scan) {
		this.deposit(scan);
	} // end optionTwo()
	
	public void optionThree(Scanner scan) {
		this.withdraw(scan);
	} // end optionThree()
	
	public void optionFour(Scanner scan) {
		this.transfer(scan);
	}
	
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
			this.displayAccounts();
			System.out.print("Enter account number: ");
			try {
				BankAccount b = accounts.get(
									accounts.indexOf(
											BankAccount.retrieve(
													scan.nextInt())));
				while(!success) {
					System.out.print("How much would you like to deposit? ");
					long amount = (long)(scan.nextDouble() * 100);
					try {
						b.deposit(amount);
						success = true;
					} catch (InvalidAmountException e) {
						System.out.println("Invalid amount! Try again.");
					} // end try-catch
				} // end while
			} catch (Exception e) {
				System.out.println("Invalid account! Try again.");
			} // end try-catch
		} // end while
		
	} // end deposit()
	
	private void withdraw(Scanner scan) {
		boolean success = false;
		
		while(!success) {
			System.out.println("Which account would you like " +
					   		   "to withdraw from?");
			this.displayAccounts();
			System.out.print("Enter account number: ");
			try {
				BankAccount b = accounts.get(
									accounts.indexOf(
											BankAccount.retrieve(
													scan.nextInt())));
				while(!success) {
					System.out.print("How much would you like to withdraw? ");
					long amount = (long)(scan.nextDouble() * 100);
					try {
						b.withdraw(amount);
						success = true;
					} catch(InvalidAmountException e) {
						System.out.println("Invalid amount! Try again.");
					} catch(NotEnoughFundsException e) {
						System.out.println("Not enough funds! Try again.");
					} // end try-catch
				} // end while
			} catch(Exception e) {
				System.out.println("Invalid account! Try again.");
			} // end try-catch
		} // end while
		
	} // end withdraw()
	
	private void transfer(Scanner scan) {
		boolean success = false;
			
		while(!success) {
			System.out.println("Which account would you " + 
							   "like to transfer from?");
			this.displayAccounts();
			System.out.print("Enter account number: ");
			try {
				BankAccount b = accounts.get(
									accounts.indexOf(
											BankAccount.retrieve(
													scan.nextInt())));
				while(!success) {
					System.out.print("Enter destination account number: ");
					try {
						BankAccount dest 
									  = BankAccount.retrieve(scan.nextInt());
						while(!success) {
							System.out.print("How much would you " +
								     		 "like to transfer? ");
							long amount = (long)(scan.nextDouble() * 100);
							try {
								b.transfer(amount, dest);
								success = true;
							} catch(InvalidAmountException e) {
								System.out.println("Invalid amount! " + 
												   "Try again.");
							} catch(NotEnoughFundsException e) {
								System.out.println("Not enough funds! " + 
												   "Try again.");
							}
						}
					} catch(AccountNotFoundException e) {
						System.out.println("Invalid account! Try again. ");
					}
				}
			} catch(Exception e) {
				System.out.println("Invalid account! Try again. ");
			}
		} // end while
	} // end transfer()
	
} // end Customer
