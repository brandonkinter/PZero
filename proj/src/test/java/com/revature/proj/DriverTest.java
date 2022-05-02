package com.revature.proj;

//import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.revature.driver.Driver;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.InvalidPhoneNumException;

public class DriverTest {
	
	Driver driver = new Driver();
	
	@Test
	public void testCheckPassword() {		
		assertDoesNotThrow( () -> {Driver.checkPassword("P@ssw0rd");} );
		
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
	public void testCheckPhoneNum() {
		assertDoesNotThrow( () -> {Driver.checkPhoneNum("123.456.7890");} );
		
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
