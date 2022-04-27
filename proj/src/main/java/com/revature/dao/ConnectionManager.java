package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static Connection connection;
	private static String cs = "jdbc:postgresql:" +
							   "//heffalump.db.elephantsql.com:5432/oxipmkdp",
					      un = "oxipmkdp",
					      pw = "HoADTrCHbDjXJzMiMkG46UfGAFDRPlix";
	
	// returns Connection to bank account database
	// if Connection not established, returns null
	public static Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(cs, un, pw);
			} // end if
			return connection;
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch(SQLException e) {
			e.printStackTrace();
			
		} // end try-catch
		
		return null;
	} // end getConnection()
	
} // end ConnectionManager
