package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;

import com.revature.models.BankAccount;

public class AccountDAO implements DAO<BankAccount, Integer, Integer> {
	
	public Integer create(BankAccount account) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO accounts (balance) " +
						     "VALUES (?) " +
						     "RETURNING acct_num;";
			PreparedStatement st = c.prepareStatement(command);
			st.setLong(1, account.getBalance());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end create()
	
	public BankAccount retrieve(Integer acctNum) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM accounts " +
							 "WHERE acct_num = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, acctNum);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				return new BankAccount(acctNum, rs.getLong(2));
			} // end if
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end retrieve()
	
	/*public ArrayList<BankAccount> retrieveByCust(Integer userID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM accounts " +
							 "INNER JOIN acct_junctions " +
							 "USING (acct_num) " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			
			ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
			
			while(rs.next()) {
				accounts.add(new BankAccount(rs.getInt(1), rs.getLong(3)));
			}
			
			return accounts;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public void update(BankAccount account) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE accounts " +
							 "SET balance = ? " +
							 "WHERE acct_num = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setLong(1, account.getBalance());
			st.setInt(2, account.getAcctNum());
			st.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
	} // end update()
	
	public void delete(BankAccount account) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM accounts " +
							 "WHERE acct_num = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, account.getAcctNum());
			st.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // end try-catch
		
	} // end delete()

} // end AccountDAO