package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;

public class UserDAO implements DAO<User, Integer, Void>{

	final Logger logger = LogManager.getLogger(UserDAO.class);
	
	public Void create(User user) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO users " +
							 "VALUES (?, ?);";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, user.getID());
			st.setString(2, user.getRole());
			st.execute();
			logger.info("created new user with id " + user.getID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
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
						logger.info("retrieved customer with id " + userID);
						return new Customer(userID, getAccounts(userID));
					case "employee":
						logger.info("retrieved employee with id " + userID);
						return new Employee(userID);
					case "admin":
						logger.info("retrieved admin with id " + userID);
						return new Admin(userID);
				}

			} // end if
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		} // end try-catch
		
		logger.error("user not found");
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
			logger.info("updated user with id of " + user.getID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
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
			logger.info("deleted user with id " + user.getID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		} // end try-catch 
		
	} // end delete()
	
	private ArrayList<Account> getAccounts(int userID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT accounts.acct_num, accounts.balance " +
						     "FROM accounts " +
						     "INNER JOIN acct_junctions " +
						     "USING(acct_num) " +
						     "WHERE acct_junctions.user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			
			ArrayList<Account> result = new ArrayList<Account>();
			
			while(rs.next()) {
				result.add(new Account(rs.getInt(1), rs.getLong(2)));
			} // end while 
			logger.info("retrieved all accounts for user " + userID);
			return result;
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		} // end try-catch
		logger.error("no accounts found for user " + userID);
		return null;
	} // end getAccounts()
	
} // end UserDAO