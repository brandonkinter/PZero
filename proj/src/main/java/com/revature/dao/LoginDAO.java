package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Login;

public class LoginDAO  implements DAO<Login, String, Integer> {
	
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
				return rs.getInt(1);
			}
			
		} catch(SQLException e) {
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
				return new Login(username, rs.getString(2), rs.getInt(3));
			} // end if
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
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
			
		} catch(SQLException e) {
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
			
		} catch(SQLException e) {
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
				return rs.getInt(1);
			} // end if
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end validate()
	
} // end LoginDAO