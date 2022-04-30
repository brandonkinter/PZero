package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.BankAccount;

public class AccountDAO implements DAO<BankAccount, Integer, Integer> {
	final Logger logger = LogManager.getLogger(AccountDAO.class);
	
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
				int id = rs.getInt(1);
				logger.info("created acct with acct_id of " + id);
				return id;
			}
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
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
				logger.info("retrieved account with id " + acctNum);
				return new BankAccount(acctNum, rs.getLong(2));
			} // end if
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		} // end try-catch
		logger.error("no account found with id " + acctNum);
		return null;
	} // end retrieve()
	
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
			logger.info("updated account with id " + account.getAcctNum());
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
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
			logger.info("deleted account with id " + account.getAcctNum());
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		} // end try-catch
		
	} // end delete()

} // end AccountDAO