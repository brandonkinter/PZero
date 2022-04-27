package com.revature.driver;

import java.util.Scanner;

import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.InvalidUsernameException;
import com.revature.exceptions.UsernameTakenException;
import com.revature.models.Customer;
import com.revature.models.Login;
import com.revature.models.User;

public class Driver {
   
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		User user = introMenu(scan);
		
		user.displayOptionsMenu();
		System.out.print("What would you like to do? ");
		
		switch(scan.nextInt()) {
			case 1:
				// do user's option 1
				break;
			case 2: // Customer--deposit, Employee/Admin--customer ops
				user.optionTwo(scan);
				break;
			case 3: // Customer--withdraw, Employee/Admin--account ops
				user.optionThree(scan);
				break;
			case 4:
				// do user's option 4
				break;
			case 5: // settings option across all User subclasses
				// settings
				break;
			case 6: // logout option across all User subclasses
				System.out.println("Thank you! We'll see you soon!");
				break;
			default:
				System.out.println("Invalid option! Try again.\n");
		}
		
		scan.close();
	} // end main()
	private static User introMenu(Scanner scan) {
		int choice = 0;
		User user = null;
		
		System.out.println("-----Welcome to Bank!-----\n");
				
		while(choice != 3) {
			System.out.println("\n");
			System.out.println("1. Log In");
			System.out.println("2. Sign Up");
			System.out.println("3. Quit");
			System.out.print("What would you like to do? ");
			choice = scan.nextInt();
			
			switch(choice) {
				case 1:
					user = login(scan);
					choice = 3;
					break;
				case 2:
					signup(scan);
					break;
				case 3:
					System.out.println("Thank you for choosing Bank! " +
									   "We'll see you soon!");
					break;
				default:
					System.out.println("Invalid option! Try again.\n");
			} // end switch
			
		} // end while
		
		return user;
	} // end displayIntroMenu()
	
	private static User login(Scanner scan) {
		boolean success = false;
		String username, password;
		User user = null;
		
		while(!success) {
			System.out.print("Please enter your username: ");
			username = scan.next();
			System.out.print("Please enter your password: ");
			password = scan.next();
			
			Login login = new Login(username, password);
			try {
				user = User.retrieve(login.validate());
				success = true;
			} catch(InvalidLoginException e) {
				System.out.println("Invalid login! Try again.\n");
			} // end try-catch
			
		} // end while
		
		return user;
	} // end login()
	
	// creates new customer and adds them to database
	private static void signup(Scanner scan) {
		boolean success = false;
		Login login = new Login();
		Customer cust = new Customer();
		
		while(!success) {
			System.out.print("\nPlease choose a username (Note - " +
					 		 "Username must contain at least 6 " +
					 		 "characters). \n\nusername: ");
			try {
				login.setUsername(scan.next());
				
				while(!success) {
					System.out.print("\nPlease choose a password (Note - " +
									 "Password must contain at least 8 " + 
									 "characters,\nincluding a number, a " +
									 "lowercase and uppercase letter, and " +
									 "one of the following\nspecial " +
									 "characters: !, ?, #, $, %, &, ^, * " +
									 ", @, +).\n\npassword: ");
					try {
						login.setPassword(scan.next());
						success = true;
					} catch(InvalidPasswordException e) {
						System.out.println("\nInvalid password! Try again.\n");
					} // end try-catch
				} // end while
				
				cust.setID(login.create());
				cust.setRole("customer");
				cust.create();
				System.out.println("\nAccount created successfully!");
				
			} catch(InvalidUsernameException e) {
				System.out.println("\nInvalid username! Try again.\n");
			} catch(UsernameTakenException e) {
				System.out.println("\nUsername already taken! Try again.\n");
			} // end try-catch
			
		} // end while
		
	} // end signup()
	
	
} // end Driver
