package com.revature.models;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.exceptions.NotFoundException;
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
	
	public static Customer retrieve(int userID) throws NotFoundException {
		User cust = userDAO.retrieve(userID);
		
		if(!(cust instanceof Customer)) 
			throw new NotFoundException();
		
		return (Customer)cust;
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
	
	public void optionOne(Scanner scan) {
		this.apply(scan);
	} // end optionOne()
	
	public void optionTwo(Scanner scan) {
		this.deposit(scan);
	} // end optionTwo()
	
	public void optionThree(Scanner scan) {
		this.withdraw(scan);
	} // end optionThree()
	
	public void optionFour(Scanner scan) {
		this.transfer(scan);
	} // end optionFour()
	
	@Override
	public String toString() {
		return "User ID: " + this.getID() +
			   "\n\nAccounts: "+ accounts;
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
	
	private void apply(Scanner scan) {
		Application app = new Application();
		boolean success = false;
		System.out.println("-----ACCOUNT APPLICATION-----\n");
		
		while(!success) {
			System.out.print("What is your annual income? ");
			try {
				app.setIncome((long)(scan.nextDouble() * 100));
				while(!success) {
					System.out.print("What would you like your " +
									 "initial deposit to be (Minimum $100)? ");
					try {
						app.setDeposit((long)(scan.nextDouble() * 100));
						app.create(this.getID());
						success = true;
					} catch(InvalidAmountException e) {
						System.out.println("Invalid deposit! Try again. \n");
					}
				}
			} catch(InvalidAmountException e) {
				System.out.println("Invalid amount! Try again. \n");
			}
		}
		System.out.println("\n\nThank you for submitting an application!");
		System.out.println("It will be reviewed shortly.");
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
					} catch(NotFoundException e) {
						System.out.println("Invalid account! Try again. ");
					}
				}
			} catch(Exception e) {
				System.out.println("Invalid account! Try again. ");
			}
		} // end while
	} // end transfer()
	
} // end Customer
