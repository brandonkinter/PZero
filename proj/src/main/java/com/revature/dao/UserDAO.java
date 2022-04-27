package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;

public class UserDAO implements DAO<User, Integer, Void>{

	public Void create(User user) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO users " +
							 "VALUES (?, ?);";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1,  user.getID());
			st.setString(2,  user.getRole());
			st.execute();

		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end create()
		
	public User retrieve(Integer userID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM users " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				String role = rs.getString(2);
				switch(role) {
					case "customer":
						return new Customer(userID);
					case "employee":
						return new Employee(userID);
					case "admin":
						return new Admin(userID);
				}

			} // end if
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end retrieve()
		
	public void update(User user) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE users " +
							 "SET user_role = ? " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setString(1, user.getRole());
			st.setInt(2, user.getID());
			st.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
	} // end update()
		
	public void delete(User user) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM users " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, user.getID());
			st.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch 
		
	} // end delete()
	
} // end UserDAO