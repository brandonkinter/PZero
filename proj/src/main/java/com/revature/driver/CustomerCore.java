package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.exceptions.InvalidAmountException;
import com.revature.exceptions.InvalidUserIDException;
import com.revature.models.AppJunction;
import com.revature.models.Application;
import com.revature.models.Customer;
import com.revature.models.User;

public class CustomerCore extends Driver {
	
	/**
	 * displays all options available to customers
	 */
	private static void displayCustomerOptions() {
		
		System.out.println("1. Apply for a new account.");
		System.out.println("2. Deposit to an account.");
		System.out.println("3. Withdraw from an account.");
		System.out.println("4. Transfer between accounts.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.\n");
		System.out.print("What would you like to do? ");
	} // end displayCustomerOptions()

	/**
	 * 
	 * handles customer's choices
	 * 
	 * @param scan
	 * @param cust
	 */
	public static void options(Scanner scan, Customer cust) {
		int choice = 0;
		
		loadAccounts(cust);
		
		while(choice != 6) {
			displayAccounts(cust);
			displayCustomerOptions();
			choice = scan.nextInt();
			
			switch(choice) {
				case 1: // apply for an account
					apply(scan, cust);
					break;
				case 2: // deposit
					deposit(scan, cust);
					break;
				case 3: // withdraw
					withdraw(scan, cust);
					break;
				case 4: // transfer
					transfer(scan, cust);
					break;
				case 5: // user profile
					userProfile(scan, cust);
					break;
				case 6: // log out
					break;
				default: // invalid input
					System.out.println("Invalid choice! Try again.");
			} // end switch
			
		} // end while
		
	} // end options()
	
	private static void apply(Scanner scan, Customer cust) {
		boolean success = false;
		
		System.out.println("\n-----Account Application-----\n");
		
		while(!success) {
			
			ArrayList<Integer> userIDs = new ArrayList<Integer>();
			userIDs.add(cust.getID());
			
			System.out.println("Is this an individual or joint application?");
			
			System.out.println("1. Individual.");
			System.out.println("2. Joint.");
			System.out.print("Choice: ");
			
			switch(scan.nextInt()) {
			
				case 2:
					System.out.println("");
					System.out.print("How many are applying, ");
					System.out.print("including yourself? ");
					int numPpl = scan.nextInt();
					System.out.println("");
					System.out.print("Please enter each user's User ID. ");
					System.out.println("Do not include your own.");
					
					for(int i = 0; i < numPpl - 1; ++i) {
						
						while(true) {
							System.out.print("User ID: ");
							
							int id = scan.nextInt();
							
							try {
								User appl = userDAO.retrieve(id);
								
								if(!(appl instanceof Customer) ||
								   id == cust.getID()) {
									throw new InvalidUserIDException();
								} // end if
								
								userIDs.add(id);
								break;
							} catch(InvalidUserIDException e) {
								System.out.println(e.getMessage());
							} // end try-catch
							
						} // end while
						
					} // end for
					
				case 1:
					System.out.println("");
					
					while(!success) {
						System.out.print("What is your annual income? ");
						
						try {
							long income = (long)(scan.nextDouble() * 100);
							
							if(income < 0)
								throw new InvalidAmountException();
							
							System.out.print("What is your initial deposit? ");
							System.out.print("(Min $100.00) ");
							long deposit = (long)(scan.nextDouble() * 100);
							
							if(deposit < 10000)
								throw new InvalidAmountException();
							
							Application app = new Application(income, deposit,
															  userIDs);
							
							int appID = appDAO.create(app);
							
							for(Integer userID : userIDs)
								appJuncDAO.create(
										new AppJunction(userID, appID));
							
							success = true;
						} catch(InvalidAmountException e) {
							System.out.println(e.getMessage());
						}
					} // end while
					break;
				default:
					System.out.println("Invalid choice! Try again.\n");
			} // end switch
		} // end while
		
		System.out.println("Application successfully submitted!");
		System.out.println("We will review it shortly.\n");
		
	} // end apply()
	
} // end CustomerCore
