package com.revature.proj;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountJunctionDAO;
import com.revature.dao.LoginDAO;
import com.revature.dao.PersonalInfoDAO;
import com.revature.dao.UserDAO;
import com.revature.driver.Driver;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.InvalidPhoneNumException;
import com.revature.exceptions.InvalidUsernameException;
import com.revature.models.Account;
import com.revature.models.AccountJunction;
import com.revature.models.Customer;
import com.revature.models.PersonalInfo;

@ExtendWith(MockitoExtension.class)
public class DriverTest {
	
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor 
											= new ByteArrayOutputStream();
	
	@Mock
	private LoginDAO loginDAO;
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private AccountJunctionDAO acctJuncDAO;
	
	@Mock
	private AccountDAO acctDAO;
	
	@Mock 
	private PersonalInfoDAO infoDAO;
	
	@BeforeEach
	public void setup() {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@AfterEach
	public void tearDown() {
		System.setOut(standardOut);
	}
	
	@Test
	public void testDisplayProfile() {
		Customer cust = new Customer(1);
		PersonalInfo info = new PersonalInfo(1, "first", "last", 1111111111);
		
		Driver.displayProfile(info);
		
		assertEquals(outputStreamCaptor.toString().trim(),
					 "-----USER PROFILE-----\n\n" +
					 "User ID: 1\n\nName: last, first\n\n" +
					 "Phone Number: 111.111.1111\n\n" +
					 "1. Update password.\n" +
					 "2. Update phone number.\n" +
					 "3. Quit.");
							 
	}
	
	@Test
	public void testDisplayNoAccounts() {
		
		Customer cust = new Customer();
		
		Driver.displayAccounts(cust);
		
		assertEquals(outputStreamCaptor.toString().trim(), 
					 "You don't have any accounts with us yet!\n" +
					 "Press '1' below to apply for an account!");
	}
	
	@Test
	public void testDisplayAccounts() {
		Customer cust = new Customer();
		
		Account acct = new Account(1, 100000);
		ArrayList<Account> accts = new ArrayList<Account>();
		accts.add(acct);
		cust.setAccounts(accts);
		
		Driver.displayAccounts(cust);
		
		assertEquals(outputStreamCaptor.toString().trim(),
					 "--- Account Number ------ Balance ---\n\n" +
					 "         " + 1 + "               $" +
					 String.format("%.2f", 100000/100.0));
	}
	
	@Test
	public void testCheckValidUsername() {
		assertNotNull(loginDAO);
		
		lenient().when(loginDAO.retrieve("username")).thenReturn(null);
		assertDoesNotThrow( () -> {Driver.checkUsername("username");} );
	}
	
	@Test 
	public void testCheckInvalidUsername() {
		Throwable exception = assertThrows(InvalidUsernameException.class,
				   						   () -> {
				   							  Driver.checkUsername("user"); 
				   						   });
		assertTrue(exception.getMessage().equals(
								"Invalid Username!. Try again.\n"));
	}
	
	@Test
	public void testLoadAccounts() {
		Customer cust = new Customer(1);
		ArrayList<AccountJunction> juncs = new ArrayList<AccountJunction>();
		ArrayList<Account> accts = new ArrayList<Account>();
		juncs.add(new AccountJunction(1, 1, 3));
		juncs.add(new AccountJunction(4, 1, 6));
		accts.add(new Account(3, 54321));
		accts.add(new Account(6, 98765));
		
		lenient().when(acctJuncDAO.retrieveByUser(1)).thenReturn(juncs);
		lenient().when(acctDAO.retrieve(3)).thenReturn(new Account(3, 54321));
		lenient().when(acctDAO.retrieve(6)).thenReturn(new Account(6, 98765));
		Driver.loadAccounts(cust);

		for(int i = 0; i < cust.getAccounts().size(); ++i)
			assertTrue(cust.getAccounts().indexOf(accts.get(i)) > 0);
	}
	
	@Test
	public void testCheckValidPassword() {		
		assertDoesNotThrow( () -> {Driver.checkPassword("P@ssw0rd");} );
	}
	
	@Test
	public void testCheckInvalidPassword() {
		Throwable exception = assertThrows(InvalidPasswordException.class, 
				   						   () -> {
				   							  Driver.checkPassword("P@ssword");
				   						   });
		assertTrue(exception.getMessage().equals(
									"Invalid password! Try again.\n"));

		exception = assertThrows(InvalidPasswordException.class, 
								 () -> {
									Driver.checkPassword("Passw0rd");
								 });
		assertTrue(exception.getMessage().equals(
									"Invalid password! Try again.\n"));

		exception = assertThrows(InvalidPasswordException.class, 
								 () -> {
									Driver.checkPassword("p@ssw0rd");
								 });
		assertTrue(exception.getMessage().equals(
									"Invalid password! Try again.\n"));	

		exception = assertThrows(InvalidPasswordException.class, 
								 () -> {
									Driver.checkPassword("P@SSW0RD");
								 });
		assertTrue(exception.getMessage().equals(
									"Invalid password! Try again.\n"));
	}
	
	@Test
	public void testCheckValidPhoneNum() {
		assertDoesNotThrow( () -> {Driver.checkPhoneNum("123.456.7890");} );
	}
	
	@Test
	public void testCheckInvalidPhoneNum() {
		Throwable exception = assertThrows(InvalidPhoneNumException.class,
				   						   () -> {
				   							  Driver.checkPhoneNum("1");
				   						   });
		assertTrue(exception.getMessage().equals(
									"Invalid phone number! Try again.\n"));

		exception = assertThrows(InvalidPhoneNumException.class,
								 () -> {
									Driver.checkPhoneNum("123456789012");
								 });
		assertTrue(exception.getMessage().equals(
									"Invalid phone number! Try again.\n"));

		exception = assertThrows(InvalidPhoneNumException.class,
								 () -> {
									Driver.checkPhoneNum("abc.def.ghij");
								 });
		assertTrue(exception.getMessage().equals(
									"Invalid phone number! Try again.\n"));
	}
}
