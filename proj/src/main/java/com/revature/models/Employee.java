package com.revature.models;

import java.util.Queue;
import java.util.Scanner;

public class Employee extends User {
	public Employee() {
		super();
	}
	
	public Employee(int userID) {
		super(userID);
		this.setRole("employee");
	}

	public void displayOptionsMenu() {
		System.out.println("1. Approve or deny applications.");
		System.out.println("2. Search for an application.");
		System.out.println("3. Search for a customer.");
		System.out.println("4. Search for an account.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	public void optionOne(Scanner scan) {
		this.processApp(scan);
	}
	
	public void optionTwo(Scanner scan) {
		
	}
	
	public void optionThree(Scanner scan) {
		
	}
	
	public void optionFour(Scanner scan) {
		
	}
	
	private void processApp(Scanner scan) {
		Queue<Application> apps = Application.retrieveAll();
		boolean choosing = true;
		
		if(!apps.isEmpty()) {
			System.out.println("There are " + apps.size() + 
							   " open applications.");
			System.out.print("Would you like to view " + 
							 "an application [Y/N]? ");
			while(!apps.isEmpty()) {
				if(scan.next().toLowerCase().equals("y")) {
					Application app = apps.poll();
					System.out.println(app);
					choosing = true;
					while(choosing) {
						System.out.println("1. Approve.");
						System.out.println("2. Deny.");
						System.out.println("3. Choose later.\n");
						System.out.print("What would you like to do? ");
						switch(scan.nextInt()) {
							case 1:
								this.approve(app);
								choosing = false;
								break;
							case 2:
								this.deny(app);
								choosing = false;
								break;
							case 3:
								choosing = false;
								break;
							default:
								System.out.println("Invalid choice! " +
												   "Try again.\n");
						}
					} // end while
				} else {
					break;
				}
				if(apps.isEmpty())
					System.out.println("No more applications!");
				else 
					System.out.print("Would you like to view another " + 
									 "application [Y/N]? ");
			}
		} else {
			System.out.println("There are no open applications!");
		}
	}
	
	private void approve(Application app) {
		// create account and junction entries
		BankAccount acct = new BankAccount(app.getDeposit());
		acct.create(app.getUserID());
			
		// delete app and junction entries
		app.delete();
	}
	
	private void deny(Application app) {
		app.delete();
	}
	
	@Override
	public String toString() {
		return "I am an employee.";
	}
}
