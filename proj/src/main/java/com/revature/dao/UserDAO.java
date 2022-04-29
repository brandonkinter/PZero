package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.BankAccount;
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
						return new Customer(userID, getAccounts(userID));
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
	
	private ArrayList<BankAccount> getAccounts(int userID) {
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
			
			ArrayList<BankAccount> result = new ArrayList<BankAccount>();
			
			while(rs.next()) {
				result.add(new BankAccount(rs.getInt(1), rs.getLong(2)));
			} // end while 
			
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end getAccounts()
	
} // end UserDAO