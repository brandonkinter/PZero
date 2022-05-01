package com.revature.models;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

import com.revature.exceptions.NotFoundException;

public class Employee extends User {
	public Employee() {
		super();
	}
	
	public Employee(int userID) {
		super(userID);
		this.setRole("employee");
	}
	
	public static Employee retrieve(int userID) throws NotFoundException {
		User empl = userDAO.retrieve(userID);
		
		if(!(empl instanceof Employee)) 
			throw new NotFoundException();
		
		return (Employee)empl;
	}

	public void displayOptionsMenu() {
		System.out.println("1. Process applications.");
		System.out.println("2. Search for applications.");
		System.out.println("3. Search for customers.");
		System.out.println("4. Search for accounts.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.");
	}
	
	public void optionOne(Scanner scan) {
		this.processApplication(scan);
	}
	
	public void optionTwo(Scanner scan) {
		this.applicationSearch(scan);
	}
	
	public void optionThree(Scanner scan) {
		this.customerSearch(scan);
	}
	
	public void optionFour(Scanner scan) {
		this.accountSearch(scan);
	}
	
	private void processApplication(Scanner scan) {
		Queue<Application> apps = Application.retrieveAllOpen();
		boolean choosing = true;
		
		if(!apps.isEmpty()) {
			for(Application app : apps) {
				ArrayList<Integer> ids 
							= AppJunction.retrieveIDsByApp(app.getAppID());
				app.setUserIDs(ids);
			}
			if(apps.size() > 1) {
				System.out.println("There are " + apps.size() + 
							   	   " open applications.");
			} else {
				System.out.println("There is one open application.");
			}
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
								Account acct = new Account(
															app.getDeposit());
								acct.create(app.getUserIDs());
								choosing = false;
								break;
							case 2:
								app.close();
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
	
	protected void applicationSearch(Scanner scan) {
		int choice = 0;
		
		displayAppSearchMenu();
		
		while(choice != 6) {
			System.out.print("What would you like to do? ");
			choice = scan.nextInt();
			switch(choice) {
				case 1:
					while(true) {
						System.out.print("\nEnter a user ID: ");
						try {
							System.out.println(Application.retrieveByCust(
									                     	scan.nextInt()));
							break;
						} catch(NotFoundException e) {
							System.out.println("No application found! " +
											   "Try again.\n");
						}
					}
					break;
				case 2:
					while(true) {
						System.out.print("\nEnter an application ID: ");
						try {
							System.out.println(Application.retrieve(
									                     	scan.nextInt()));
							break;
						} catch(NotFoundException e) {
							System.out.println("No application found! " +
											   "Try again.\n");
						}
					}
					break;
				case 3:
					System.out.println(Application.retrieveAllOpen());
					break;
				case 4:
					System.out.println(Application.retrieveAllClosed());
					break;
				case 5:
					System.out.println(Application.retrieveAll());
					break;
				case 6:
					break;
				default:
					System.out.println("Invalid choice! Try again.");
			}
		}
	}
	
	protected void customerSearch(Scanner scan) {
		System.out.print("Enter a user ID: ");
			
		try {
			System.out.println(Customer.retrieve(scan.nextInt()));
		} catch(NotFoundException e) {
			System.out.println("Customer not found!");
		}
			
	}
	
	protected void accountSearch(Scanner scan) {
		System.out.print("Enter an account number: ");
		
		try {
			int acctNum = scan.nextInt();
			System.out.println(Account.retrieve(acctNum));
			System.out.println("Users: " + Account.retrieveCusts(acctNum));
		} catch(NotFoundException e) {
			System.out.println("Account not found!");
		}
	}
	
	private void displayAppSearchMenu() {
		System.out.println("1. Search by customer.");
		System.out.println("2. Search by application ID.");
		System.out.println("3. View all open applications.");
		System.out.println("4. View all closed applications.");
		System.out.println("5. View all applications.");
		System.out.println("6. Quit.\n");
	}
	
	@Override
	public String toString() {
		return "I am an employee.";
	}
}
