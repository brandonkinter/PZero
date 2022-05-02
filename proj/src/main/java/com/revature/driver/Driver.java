package com.revature.driver;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.controller.AccountController;
import com.revature.controller.LoginController;
import com.revature.controller.ApplicationController;
import com.revature.dao.AccountDAO;
import com.revature.dao.AccountJunctionDAO;
import com.revature.dao.AppJunctionDAO;
import com.revature.dao.ApplicationDAO;
import com.revature.dao.LoginDAO;
import com.revature.dao.PersonalInfoDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InvalidAmountException;
import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.InvalidPhoneNumException;
import com.revature.exceptions.InvalidUsernameException;
import com.revature.exceptions.NotEnoughFundsException;
import com.revature.exceptions.UsernameTakenException;
import com.revature.models.Account;
import com.revature.models.AccountJunction;
import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Login;
import com.revature.models.PersonalInfo;
import com.revature.models.User;

import io.javalin.Javalin;

public class Driver {
	
	protected static LoginDAO loginDAO = new LoginDAO();
	protected static UserDAO userDAO = new UserDAO();
	protected static AccountDAO acctDAO = new AccountDAO();
	protected static AccountJunctionDAO acctJuncDAO = new AccountJunctionDAO();
	protected static ApplicationDAO appDAO = new ApplicationDAO();
	protected static AppJunctionDAO appJuncDAO = new AppJunctionDAO();
	protected static PersonalInfoDAO infoDAO = new PersonalInfoDAO();
   
	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
		Javalin app = Javalin.create().start(7070);
		
		LoginController loginController = new LoginController(app);
		AccountController acctController = new AccountController(app);
		ApplicationController appController = new ApplicationController(app);
		
		Scanner scan = new Scanner(System.in);
		
		int choice = 0;
		
		User user;
		
		while(choice != 3) {
			displayStartupMenu();
			choice = scan.nextInt();
			
			switch(choice) {
				case 2: // signup
					signup(scan);
				case 1: // login
					user = login(scan);
					
					if(user instanceof Customer) { // if customer
						CustomerCore.options(scan, (Customer)user);
						
					} else if(user instanceof Admin) { // if admin
						AdminCore.options(scan, (Admin)user);
						
					} else { // if employee
						EmployeeCore.options(scan, (Employee)user);
						
					} // end if-else
					
					break;
				case 3: // quit
					System.out.println("Thank you! We'll see you again soon.");
					break;
				default: // invalid input
					System.out.println("Invalid choice! Try again.");
			} // end switch
		} // end while
		
		scan.close();
	} // end main()
	
	/**
	 * 
	 * displays initial startup menu for all users
	 * 
	 */
	private static void displayStartupMenu() {
		System.out.println("-----Welcome to Revature Bank!-----\n\n");
		
		System.out.println("What would you like to do?\n");
		
		System.out.println("1. Log In.");
		System.out.println("2. Sign Up.");
		System.out.println("3. Quit.\n");
		
		System.out.print("Enter your choice here: ");
	} // end displayStartupMenu()
	
	/**
	 * 
	 * Verifies that all requirements of a valid username are met
	 * 
	 * @param  username
	 * @throws InvalidUsernameException if username is taken or too short
	 * 
	 */
	private static void checkUsername(String username) 
										throws InvalidUsernameException,
											   UsernameTakenException {
		if(username.length() < 6)
			throw new InvalidUsernameException();
		
		if(loginDAO.retrieve(username) != null)
			throw new UsernameTakenException();
	} // end checkUsername()
	
	/**
	 * 
	 * Verifies that all requirements of a valid password are met
	 * 
	 * @param  password
	 * @throws InvalidPasswordException if password is too short or
	 * 		   doesn't contain a necessary character
	 * 
	 */
	public static void checkPassword(String password)
										throws InvalidPasswordException {
		// strings to check against
		String special = "!?3$&^@+",
			   nums = "0123456789",
			   letters = "abcdefghijklmnopqrstuvwxyz";
	
		// initialize necessary containers to false
		boolean hasSpecial, hasNum, hasLetter, hasCap;
		hasSpecial = hasNum = hasLetter = hasCap = false;
						
		int length = password.length();
						
		// if password too short
		if(length < 8) 
			throw new InvalidPasswordException();
						
		// check password for necessary characters
		for(int i = 0; i < length; ++i) {
			char c = password.charAt(i);
							
			if(special.indexOf(c) > -1) // if password has special char
				hasSpecial = true;
			else if(nums.indexOf(c) > -1) // if it has number
				hasNum = true;
			else if(letters.indexOf(c) > -1) // if it has letter
				hasLetter = true;
			else if(letters.toUpperCase().indexOf(c) > -1) // if it has capital
				hasCap = true;
		} // end for
						
		// if password is missing a necessary character
		if(!(hasSpecial && hasNum && hasLetter && hasCap))
			throw new InvalidPasswordException();
	} // end checkPassword()
	
	private static void updatePhoneNumber(Scanner scan, PersonalInfo info) {
		
		System.out.print("Enter your new phone number (xxx.xxx.xxxx): ");
		String phoneNum = scan.next();
		
		try {
			long num = checkPhoneNum(phoneNum);
			
			// update personal_info entry in database
			info.setPhoneNum(num);
			infoDAO.update(info);
			
			System.out.println("Phone number successfully updated!\n");
			
		} catch(InvalidPhoneNumException e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end updatePhoneNumber()
	
	private static void updatePassword(Scanner scan, User user) {
		Login login = loginDAO.retrieve(user.getID());
		
		System.out.println("Please choose a password (Note - " +
				   		   "Password must contain at least 8\n" +
				   		   "characters, including a number, a " +
				   		   "special character, and an uppercase\n" +
						   "and lowercase letter).\n");
		System.out.print("New password: ");
		String password = scan.next();
		
		try {
			checkPassword(password); // make sure password is valid
			
			// make sure new password isn't same as old password
			if(password.equals(login.getPassword()))
				throw new InvalidPasswordException();
			
			// update login entry in database
			login.setPassword(password);
			loginDAO.update(login);
			
			System.out.println("Password successfully updated!");
			
		} catch(InvalidPasswordException e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end updatePassword()
	
	/**
	 * 
	 * Obtains username and password from user on command line,
	 * verifies that both the username and password are valid,
	 * and creates new Login and Customer entries in the database
	 * 
	 * @param  scan
	 *  
	 */
	private static void signup(Scanner scan) {
		
		String username, password, firstName, lastName, phoneNum;
		
		// loops until valid username and password are entered
		while(true) {
			System.out.println("");
			// receive username from command line
			System.out.println("Please choose a username (Note - " +
	 		 		   		   "Username must contain at least 6 " +
	 		 		   		   "characters).\n");
			System.out.print("Username: ");
			username = scan.next();
			System.out.println("");
			
			try {
				checkUsername(username);
				
				// receive password from command line
				System.out.println("Please choose a password (Note - " +
								   "Password must contain at least 8\n" +
								   "characters, including a number, a " +
								   "special character, and an uppercase\n" +
								   "and lowercase letter).\n");
				System.out.print("Password: ");
				password = scan.next();
				
				checkPassword(password);
				
				break;
			} catch(Exception e) {
				System.out.println(e.getMessage());
			} // end try-catch
		} // end while	 		  
		
		// username and password are valid, so create entries
		Login login = new Login(username, password);
		int userID = loginDAO.create(login);
		// creates login entry in database, stores userID in new Customer
		Customer cust = new Customer(userID);
		// creates customer entry in database
		userDAO.create(cust);
		
			System.out.println("");
			
			System.out.print("Enter your first name: ");
			firstName = scan.next();
			
			System.out.print("Enter your last name: ");
			lastName = scan.next();
			
		while(true) {
			try {
				System.out.print("Enter your phone number (xxx.xxx.xxxx): ");
				phoneNum = scan.next();
				
				long num = checkPhoneNum(phoneNum);
			
				PersonalInfo info 
						= new PersonalInfo(userID, firstName, lastName, num);
				infoDAO.create(info);
				
				break;
			} catch(InvalidPhoneNumException e) {
				System.out.println(e.getMessage());
			}
		} // end while
		
		System.out.println("User account successfully created!");
		System.out.println("Please log in with your credentials.\n");
	} // end signup()
	
	public static long checkPhoneNum(String phoneNum) 
									throws InvalidPhoneNumException {
		String nums = "0123456789",
			   temp = "";
		
		if(phoneNum.length() != 12)
			throw new InvalidPhoneNumException();
		
		for(int i = 0; i < 12; ++i) {
			char c = phoneNum.charAt(i);
			if(i == 3 || i == 7) {
				if(c != '.')
					throw new InvalidPhoneNumException();
			} else if(nums.indexOf(c) < 0){
				throw new InvalidPhoneNumException();
			} // end if-else
		} // end for
		
		temp += phoneNum.substring(0, 3);
		temp += phoneNum.substring(4, 7);
		temp += phoneNum.substring(8);
		
		return Long.parseLong(temp);
		
	} // end checkPhoneNum()
	
	/**
	 * 
	 * Obtains username and password from user on command line,
	 * validates username/password pair in the database,
	 * and retrieves the correct User for that login
	 * 
	 * @param  scan
	 * @return User profile from command line user login
	 */
	private static User login(Scanner scan) {
		
		String username, password;
		
		while(true) {
			System.out.println("");
			System.out.print("Username: ");
			username = scan.next();
			
			System.out.print("Password: ");
			password = scan.next();
			
			Login login = new Login(username, password);
			
			try {
				int userID = loginDAO.validate(login);
				
				System.out.println("\nLogin successful!\n");
				
				return userDAO.retrieve(userID);
			} catch(InvalidLoginException e) {
				System.out.println(e.getMessage());
			}
		} // end while
		
	} // end login()
	
	protected static void userProfile(Scanner scan, User user) {
		int choice = 0;
		
		PersonalInfo info = infoDAO.retrieve(user.getID());
			
		while(choice != 3) {
			displayProfile(info);
			System.out.print("What would you like to do? ");
			choice = scan.nextInt();
				
			switch(choice) {
				case 1: // update password
					updatePassword(scan, user);
					break;
				case 2: // update phone number
					updatePhoneNumber(scan, info);
					break;
				case 3: // quit
					break;
				default:
					System.out.println("Invalid choice! Try again.");
			} // end switch
		} // end while
	} // end userProfile()
	
	private static void displayProfile(PersonalInfo info) {
		System.out.println("\n-----USER PROFILE-----\n");
		System.out.println(info);
		System.out.println("1. Update password.");
		System.out.println("2. Update phone number.");
		System.out.println("3. Quit.\n");
	} // end displayProfile()
	
	protected static void loadAccounts(Customer cust) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		ArrayList<AccountJunction> juncs 
									= acctJuncDAO.retrieveByUser(cust.getID());
		
		for(AccountJunction junc : juncs) 
			accounts.add(acctDAO.retrieve(junc.getAcctNum()));
		
		
		cust.setAccounts(accounts);
	} // end loadAccounts()
	
	protected static void deposit(Scanner scan, Customer cust) {
		
		System.out.println("Which account would you like to deposit to?\n");
		
		displayAccounts(cust);
		
		System.out.print("Account number: ");
		int acctNum = scan.nextInt();
		
		try {
			Account acct = acctDAO.retrieve(acctNum);
			int index = cust.getAccounts().indexOf(acct);
			
			if(index < 0)
				throw new AccountNotFoundException();
			
			System.out.print("How much would you like to deposit? ");
			long amount = (long)(scan.nextDouble() * 100);
			
			if(amount < 0)
				throw new InvalidAmountException();
			
			// updates balance in user's ArrayList
			Account temp = cust.getAccounts().get(index);
			temp.setBalance(acct.getBalance() + amount);
			
			// updates balance in database
			acct.setBalance(acct.getBalance() + amount);
			acctDAO.update(acct);
			
			System.out.println("Deposit successful!\n");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end deposit()
	
	protected static void withdraw(Scanner scan, Customer cust) {
		
		System.out.println("Which account would you like to withdraw from?\n");
		
		displayAccounts(cust);
		
		System.out.print("Account number: ");
		int acctNum = scan.nextInt();
		
		try {
			Account acct = acctDAO.retrieve(acctNum);
			int index = cust.getAccounts().indexOf(acct);
			
			if(index < 0)
				throw new AccountNotFoundException();
			
			System.out.print("How much would you like to withdraw? ");
			long amount = (long)(scan.nextDouble() * 100);
			
			if(amount < 0)
				throw new InvalidAmountException();
			
			if(acct.getBalance() - amount < 0)
				throw new NotEnoughFundsException();
			
			// updates balance in user's ArrayList
			Account temp = cust.getAccounts().get(index);
			temp.setBalance(acct.getBalance() - amount);
			
			// updates balance in database
			acct.setBalance(acct.getBalance() - amount);
			acctDAO.update(acct);
			
			System.out.println("Withdraw successful!\n");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end withdraw()
	
	protected static void transfer(Scanner scan, Customer cust) {
		System.out.println("Which account would you like to transfer from?\n");
		
		displayAccounts(cust);
		
		System.out.print("Account number: ");
		int acctNum = scan.nextInt();
		
		try {
			Account sourceAcct = acctDAO.retrieve(acctNum);
			int index = cust.getAccounts().indexOf(sourceAcct);
			
			if(index < 0)
				throw new AccountNotFoundException();
			
			System.out.print("Which account would you ");
			System.out.println("like to transfer to?\n");
			
			System.out.print("Account number: ");
			acctNum = scan.nextInt();
			
			Account destAcct = acctDAO.retrieve(acctNum);
			
			if(destAcct == null)
				throw new AccountNotFoundException();
			
			System.out.print("How much would you like to transfer? ");
			long amount = (long)(scan.nextDouble() * 100);
			
			if(amount < 0)
				throw new InvalidAmountException();
			
			if(sourceAcct.getBalance() - amount < 0)
				throw new NotEnoughFundsException();
			
			// updates source balance in user's ArrayList
			Account temp = cust.getAccounts().get(index);
			temp.setBalance(sourceAcct.getBalance() - amount);
			index = cust.getAccounts().indexOf(destAcct);
			
			// updates source balance in database
			sourceAcct.setBalance(sourceAcct.getBalance() - amount);
			acctDAO.update(sourceAcct);
			
			if(index > -1) {
				temp = cust.getAccounts().get(index);
				temp.setBalance(destAcct.getBalance() + amount);
			} // end if
			
			// updates destination balance in database
			destAcct.setBalance(destAcct.getBalance() + amount);
			acctDAO.update(destAcct);
			
			System.out.println("Transfer successful!\n");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} // end try-catch
		
	} // end transfer()
	
	protected static void displayAccounts(Customer cust) {
		ArrayList<Account> accounts = cust.getAccounts();
		
		if(accounts.isEmpty()) {
			System.out.println("You don't have any accounts with us yet!");
			System.out.println("Press '1' below to apply for an account!");
		} else {
			System.out.println("\n--- Account Number ------ Balance ---\n");
			
			for(Account a : accounts) {
				String balance = String.format("%.2f", a.getBalance()/100.0);
				System.out.print("         " + a.getAcctNum());
				System.out.print("               $");
				System.out.println(balance);
			} // end for
		} // end if-else
		
		System.out.println("\n");
	} // end displayAccounts()
	
} // end Driver
