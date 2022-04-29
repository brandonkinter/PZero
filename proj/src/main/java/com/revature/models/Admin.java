package com.revature.models;

import java.util.Scanner;

import com.revature.exceptions.InvalidAmountException;
import com.revature.exceptions.NotEnoughFundsException;
import com.revature.exceptions.NotFoundException;

public class Admin extends Employee {
	public Admin() {
		super();
	}
	
	public Admin(int userID) {
		super(userID);
		this.setRole("admin");
	}
	
	public void displayOptionsMenu() {
		System.out.println("1. Approve or deny applications.");
		System.out.println("2. Search.");
		System.out.println("3. Make a transaction.");
		System.out.println("4. Cancel an account.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	@Override
	public void optionTwo(Scanner scan) {
		int choice = 0;
		
		while(choice != 5) {
			this.displaySearchMenu();
			System.out.print("What would you like to do? ");
			choice = scan.nextInt();
			
			switch(choice) {
				case 1:
					super.applicationSearch(scan);
					break;
				case 2:
					super.customerSearch(scan);
					break;
				case 3:
					super.accountSearch(scan);
					break;
				case 4:
					this.employeeSearch(scan);
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid choice! Try again.\n");
			}
		}
	}
	
	@Override
	public void optionThree(Scanner scan) {
		boolean success = false;
		
		while(!success) {
			System.out.println("What account would you like to access?");
			System.out.print("Enter account number: ");
			
			try {
				BankAccount acct = BankAccount.retrieve(scan.nextInt());
				while(!success) {
					this.displayTransactionsMenu();
					System.out.print("What would you like to do? ");
					switch(scan.nextInt()) {
						case 1:
							this.deposit(scan, acct);
							success = true;
							break;
						case 2:
							this.withdraw(scan, acct);
							success = true;
							break;
						case 3:
							this.transfer(scan, acct);
							success = true;
							break;
						case 4:
							success = true;
							break;
						default:
							System.out.println("Invalid choice! Try again.");
					}
				}
				
			} catch(NotFoundException e) {
				System.out.println("Account not found! Try again.\n");
			}
		}
	}
	
	private void displaySearchMenu() {
		System.out.println("1. Search for applications.");
		System.out.println("2. Search for customers.");
		System.out.println("3. Search for accounts.");
		System.out.println("4. Search for employees.");
		System.out.println("5. Quit.");
	}
	
	private void displayTransactionsMenu() {
		System.out.println("1. Deposit.");
		System.out.println("2. Withdraw.");
		System.out.println("3. Transfer");
		System.out.println("4. Quit.");
	}
	
	private void employeeSearch(Scanner scan) {
		System.out.print("Enter a user ID: ");
		
		try {
			System.out.println(Employee.retrieve(scan.nextInt()));
		} catch(NotFoundException e) {
			System.out.println("Employee not found!");
		}
	}
	
	private void deposit(Scanner scan, BankAccount b) {
		boolean success = false;
		
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
	}
	
	private void withdraw(Scanner scan, BankAccount b) {
		boolean success = false;
		
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
	}
	
	private void transfer(Scanner scan, BankAccount b) {
		boolean success = false;
		
		while(!success) {
			System.out.print("Enter destination account number: ");
			try {
				BankAccount dest = BankAccount.retrieve(scan.nextInt());
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
	}
	
	@Override
	public String toString() {
		return "I am an admin.";
	}
}
