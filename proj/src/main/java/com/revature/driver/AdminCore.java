package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.AccountJunction;
import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.PersonalInfo;
import com.revature.models.User;

public class AdminCore extends EmployeeCore {

	/**
	 * displays all options available to admins
	 */
	private static void displayAdminOptions() {
		System.out.println("1. Approve or deny applications.");
		System.out.println("2. Search.");
		System.out.println("3. Make a transaction.");
		System.out.println("4. Cancel an account.");
		System.out.println("5. User profile.");
		System.out.println("6. Log Out.\n");
		System.out.print("What would you like to do? ");
	} // end displayAdminOptions()
	
	/**
	 * 
	 * handles admin's choices
	 * 
	 * @param scan
	 * @param admin
	 */
	public static void options(Scanner scan, Admin admin) {
		int choice = 0;
		
		while(choice != 6) {
			displayAdminOptions();
			choice = scan.nextInt();
			
			switch(choice) {
				case 1: // process applications
					processOptions(scan);
					break;
				case 2: // search features
					search(scan);
					break;
				case 3: // transactions
					transactionOptions(scan);
					break;
				case 4: // cancel accounts
					cancel(scan);
					break;
				case 5: // user profile
					userProfile(scan, admin);
					break;
				case 6: // log out
					break;
				default: // invalid input
					System.out.println("Invalid choice! Try again.");
			} // end switch
		} // end while
	} // end options()
	
	private static void search(Scanner scan) {
		int choice = 0;
		
		while(choice != 5) {
			displaySearchMenu();
			choice = scan.nextInt();
			
			switch(choice) {
				case 1: // application search
					applicationSearch(scan);
					break;
				case 2: // customer search
					customerSearch(scan);
					break;
				case 3: // account search
					accountSearch(scan);
					break;
				case 4: // employee search
					employeeSearch(scan);
					break;
				case 5: // quit
					break;
				default:
					System.out.println("Invalid choice! Try again.");
					
			} // end switch
		} // end while
	} // end search()
	
	private static void displaySearchMenu() {
		System.out.println("1. Search for applications.");
		System.out.println("2. Search for customers.");
		System.out.println("3. Search for accounts.");
		System.out.println("4. Search for employees.");
		System.out.println("5. Quit.\n");
		System.out.print("What would you like to do? ");
	} // end displaySearchMenu()
	
	private static void displayTransactionsMenu() {
		System.out.println("\n---OPTIONS---\n");
		System.out.println("1. Deposit.");
		System.out.println("2. Withdraw.");
		System.out.println("3. Transfer");
		System.out.println("4. Quit.\n");
		System.out.print("What would you like to do? ");
	} // end displayTransactionsMenu()
	
	public static void employeeSearch(Scanner scan) {
		System.out.print("Enter a User ID: ");
		int userID = scan.nextInt();
		
		try {
			User user = userDAO.retrieve(userID);
				
			if(!(user instanceof Employee))
				throw new UserNotFoundException();
			
			PersonalInfo info = infoDAO.retrieve(userID);
			
			System.out.println(info);
			System.out.println("Role: " + user.getRole() + "\n");
				
		} catch(UserNotFoundException e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end employeeSearch()
	
	private static void transactionOptions(Scanner scan) {
		int choice = 0;
		
		System.out.println("Which user would you like to access?\n");
		System.out.print("User ID: ");
		
		try {
			User user = userDAO.retrieve(scan.nextInt());
			
			if(!(user instanceof Customer))
				throw new UserNotFoundException();
			
			loadAccounts((Customer)user);
			
			while(choice != 4) {
				displayTransactionsMenu();
				choice = scan.nextInt();
				
				switch(choice) {
					case 1: // deposit
						deposit(scan, (Customer)user);
						break;
					case 2: // withdraw
						withdraw(scan, (Customer)user);
						break;
					case 3: // transfer
						transfer(scan, (Customer)user);
						break;
					case 4: // quit
						break;
					default:
						System.out.println("Invalid choice! Try again.");
				} // end switch
			} // end while
			
		} catch(UserNotFoundException e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end transactionOptions()
	
	private static void cancel(Scanner scan) {
		System.out.print("Enter an account number: ");
		int acctNum = scan.nextInt();
		
		try {
			Account acct = acctDAO.retrieve(acctNum);
			ArrayList<AccountJunction> juncs
								= acctJuncDAO.retrieveByAcct(acctNum);
			
			if(acct == null)
				throw new AccountNotFoundException();
			
			System.out.println(acct + "\n");
			
			System.out.println("User IDs");
			System.out.println("--------");
			for(AccountJunction junc : juncs) {
				System.out.println(junc.getUserID());
			}
			
			System.out.println("");
			System.out.println("Are you sure?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			System.out.print("Enter decision: ");
			
			switch(scan.nextInt()) {
				case 1: // delete account and all associated junction entries
					for(AccountJunction junc : juncs)
						acctJuncDAO.delete(junc);
					
					acctDAO.delete(acct);
					
					System.out.println("Account successfully canceled!\n");
					break;
				case 2: // quit
					System.out.println("Account not canceled.");
					break;
				default:
					System.out.println("Invalid choice! Try again.\n");
			} // end switch
			
		} catch(AccountNotFoundException e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end cancel()
	
} // end AdminCore
