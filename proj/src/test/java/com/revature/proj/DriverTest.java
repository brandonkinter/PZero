package com.revature.proj;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.revature.exceptions.InvalidPasswordException;
import com.revature.models.Login;

public class DriverTest {
	
	Login login = new Login();
	
	@Test
	public void testCheckPassword() {		
		assertDoesNotThrow( () -> {login.checkPassword("P@ssw0rd");} );
		
		Throwable exception = assertThrows(InvalidPasswordException.class, 
										   () -> {
											   login.checkPassword("P@ssword");
										   });
		assertTrue(exception.getMessage().equals("Invalid password!"));
		
		exception = assertThrows(InvalidPasswordException.class, 
				   				 () -> {
				   					 login.checkPassword("Passw0rd");
				   				 });
		assertTrue(exception.getMessage().equals("Invalid password!"));
		
		exception = assertThrows(InvalidPasswordException.class, 
  				 				 () -> {
  				 					 login.checkPassword("p@ssw0rd");
  				 				 });
		assertTrue(exception.getMessage().equals("Invalid password!"));	
		
		exception = assertThrows(InvalidPasswordException.class, 
  				 				 () -> {
  				 					 login.checkPassword("P@SSW0RD");
  				 				 });
		assertTrue(exception.getMessage().equals("Invalid password!"));
	}
}
