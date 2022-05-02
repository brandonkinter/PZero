package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Login;

public class LoginDAO  implements DAO<Login, String, Integer> {
	final Logger logger = LogManager.getLogger(LoginDAO.class);
	
	public Integer create(Login login) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO logins (username, passcode) " +
							 "VALUES (?, ?) " +
							 "RETURNING user_id;";
			PreparedStatement st = c.prepareStatement(command);
			st.setString(1,  login.getUsername());
			st.setString(2,  login.getPassword());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt(1);
				logger.info("create login success, generated userID of " + id);
				return id;
			}
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
			e.printStackTrace();
		} // end try-catch
		return null;
	} // end create()
	
	public Login retrieve(String username) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM logins " +
							 "WHERE username = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				logger.info("retrieved login of username " + username);
				return new Login(username, rs.getString(2), rs.getInt(3));
			} // end if
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		} // end try-catch
		logger.info("no login found with username " + username);
		return null;
	} // end retrieve()
	
	public Login retrieve(int userID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM logins " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				logger.info("retrieved login from userID " + userID);
				return new Login(rs.getString(1), rs.getString(2), userID);
			} // end if
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		} // end try-catch
		logger.info("no login found from userID " + userID);
		return null;
	} // end retrieve()
	
	// updates password field
	public void update(Login login) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE logins " +
							 "SET passcode = ? " +
							 "WHERE username = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setString(1, login.getPassword());
			st.setString(2, login.getUsername());
			st.execute();
			logger.info("updated password for username " + 
						login.getUsername());
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
			e.printStackTrace();
		} // end try-catch
		
	} // end update()
	
	public void delete(Login login) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM logins " +
							 "WHERE username = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setString(1, login.getUsername());
			st.execute();
			logger.info("deleted login for username " + login.getUsername());
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		} // end try-catch
		
	} // end delete()
	
	public Integer validate(Login login) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT user_id " +
							 "FROM logins " +
							 "WHERE username = ? " +
							 "AND passcode = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setString(1, login.getUsername());
			st.setString(2, login.getPassword());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt(1);
				logger.info("validated login for user with id " + id);
				return id;
			} 
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting validate");
			e.printStackTrace();
		} // end try-catch
		logger.info("no valid login found");
		return null;
	} // end validate()
	
} // end LoginDAO