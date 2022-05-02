package com.revature.driver;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.AppNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.AccountJunction;
import com.revature.models.Application;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;

public class EmployeeCore extends Driver {

	/**
	 * displays all options available to employees
	 */
	private static void displayEmployeeOptions() {
		System.out.println("1. Process applications.");
		System.out.println("2. Search for applications.");
		System.out.println("3. Search for customers.");
		System.out.println("4. Search for accounts.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.\n");
		System.out.print("What would you like to do? ");
	} // end displayEmployeeOptions()
	
	/**
	 * 
	 * handles employee's choices
	 * 
	 * @param scan
	 * @param empl
	 */
	public static void options(Scanner scan, Employee empl) {
		int choice = 0;
		
		while(choice != 6) {
			displayEmployeeOptions();
			choice = scan.nextInt();
			
			switch(choice) {
				case 1: // approve or deny applications
					processOptions(scan);
					break;
				case 2: // search for applications
					applicationSearch(scan);
					break;
				case 3: // search for customers
					customerSearch(scan);
					break;
				case 4: // search for accounts
					accountSearch(scan);
					break;
				case 5: // user profile
					userProfile(scan, empl);
					break;
				case 6: // log out
					break;
				default: // invalid input
					System.out.println("Invalid choice! Try again.");
			} // end switch
		} // end while
	} // end options()
	
	protected static void processOptions(Scanner scan) {
		boolean choosing = true;
		Queue<Application> apps = appDAO.retrieveAllOpen();
		
		for(Application app : apps)
			app.setUserIDs(appJuncDAO.retrieveIDsByApp(app.getAppID()));
		
		if(apps.isEmpty())
			System.out.println("There are no open application!\n");
		else {
			if(apps.size() == 1)
				System.out.println("There is one open application.\n");
			else {
				System.out.print("There are " + apps.size());
				System.out.println(" open applications.\n");
			} // end if-else
			
			System.out.println("View an application? ");
			System.out.println("1. Yes");
			System.out.println("2. No\n");
			
			while(!apps.isEmpty() && choosing) {
				System.out.print("What would you like to do? ");
				
				switch(scan.nextInt()) {
					case 1:
						Application app = apps.poll();
						System.out.println(app + "\n");
						processApplication(scan, app);
						break;
					case 2:
						choosing = false;
						break;
					default:
						System.out.println("Invalid choice! Try again.\n");
				} // end switch
				
				if(apps.isEmpty()) {
					System.out.println("No more applications!\n");
				} else if(choosing) {
					System.out.println("View another application? ");
					System.out.println("1. Yes");
					System.out.println("2. No\n");
				} // end if-else
			} // end while
		} // end if-else
	} // end processOptions()
	
	private static void processApplication(Scanner scan, Application app) {		
		displayProcessOptions();
		
		switch(scan.nextInt()) {
			case 1: // approve applications
				// create account in database
				int acctNum = acctDAO.create(new Account(app.getDeposit()));
				
				// create entry for each user in acct_junctions in database
				for(Integer userID : app.getUserIDs())
					acctJuncDAO.create(new AccountJunction(userID, acctNum));
				
			case 2: // deny application
				// closes application
				appDAO.update(app);
				break;
			case 3: // choose later
				break;
			default:
				System.out.println("Invalid choice! Try again.\n");
		} // end switch
		
	} // end processApplication()
	
	protected static void applicationSearch(Scanner scan) {
		int choice = 0;
		
		while(choice != 6) {
			displayAppSearchMenu();
			choice = scan.nextInt();
			
			switch(choice) {
				case 1: // search apps by customer
					System.out.print("Enter a User ID: ");
					
					try {
						Queue<Application> apps 
									= appDAO.retrieveByCust(scan.nextInt());
						if(apps.isEmpty())
							throw new AppNotFoundException();
							
						System.out.println(apps);
						
					} catch(AppNotFoundException e) {
						System.out.println(e.getMessage());
					} // end try-catch
					
					break;
				case 2: // search apps by ID
					System.out.print("Enter an application ID: ");
					
					try {
						Application app = appDAO.retrieve(scan.nextInt());
						
						if(app == null)
							throw new AppNotFoundException();
						
						System.out.println(app);
					} catch(AppNotFoundException e) {
						System.out.println(e.getMessage());
					} // end try-catch
					
					break;
				case 3: // view all open apps
					try {
						Queue<Application> apps = appDAO.retrieveAllOpen();
						
						if(apps.isEmpty())
							throw new AppNotFoundException();
						
						System.out.println(apps);
					} catch(AppNotFoundException e) {
						System.out.println(e.getMessage());
					} // end try-catch
					
					break;
				case 4: // view all closed apps
					try {
						Queue<Application> apps = appDAO.retrieveAllClosed();
						
						if(apps.isEmpty())
							throw new AppNotFoundException();
						
						System.out.println(apps);
					} catch(AppNotFoundException e) {
						System.out.println(e.getMessage());
					} // end try-catch
					
					break;
				case 5: // view all apps
					try {
						Queue<Application> apps = appDAO.retrieveAll();
						
						if(apps.isEmpty())
							throw new AppNotFoundException();
						
						System.out.println(apps);
					} catch(AppNotFoundException e) {
						System.out.println(e.getMessage());
					} // end try-catch
					
					break;
				case 6: // quit
					break;
				default:
					System.out.println("Invalid choice! Try again.");
			} // end switch
		} // end while
		
	} // end applicationSearch()
	
	protected static void customerSearch(Scanner scan) {
		System.out.print("Enter a User ID: ");
		
		try {
			User user = userDAO.retrieve(scan.nextInt());
			
			if(!(user instanceof Customer))
				throw new UserNotFoundException();
			
			loadAccounts((Customer)user);
			System.out.println(user + "\n");
			
		} catch(UserNotFoundException e) {
			System.out.println(e.getMessage());
		} // end try-catch
	} // end customerSearch()
	
	protected static void accountSearch(Scanner scan) {
		System.out.print("Enter an account number: ");
		int acctNum = scan.nextInt();
		
		try {
			Account acct = acctDAO.retrieve(acctNum);
			
			if(acct == null)
				throw new AccountNotFoundException();
			
			ArrayList<AccountJunction> juncs 
								= acctJuncDAO.retrieveByAcct(acctNum);
			System.out.println(acct);
			System.out.print("Users: " + juncs.get(0).getUserID());
			juncs.remove(0);
			for(AccountJunction junc : juncs) 
				System.out.print(", " + junc.getUserID());
			
			System.out.println("");
		} catch(AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
	} // end accountSearch()
	
	private static void displayAppSearchMenu() {
		System.out.println("\n-----OPTIONS-----\n");
		System.out.println("1. Search by customer.");
		System.out.println("2. Search by application ID.");
		System.out.println("3. View all open applications.");
		System.out.println("4. View all closed applications.");
		System.out.println("5. View all applications.");
		System.out.println("6. Quit.\n");
		System.out.print("What would you like to do? ");
	} // end displayAppSearchMenu()
	
	private static void displayProcessOptions() {
		System.out.println("---OPTIONS---\n");
		System.out.println("1. Approve.");
		System.out.println("2. Deny.");
		System.out.println("3. Review later.\n");
		System.out.print("What would you like to do? ");
	} // end displayProcessOptions()
	
} // end EmployeeCore
