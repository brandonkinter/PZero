package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.AccountJunction;

public class AccountJunctionDAO implements DAO<AccountJunction, Integer, Void> {
	final Logger logger = LogManager.getLogger(AccountJunctionDAO.class);
	
	public Void create(AccountJunction junction) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO acct_junctions (user_id, acct_num)" +
							 "VALUES (?, ?);";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, junction.getUserID());
			st.setInt(2, junction.getAcctNum());
			st.execute();
			logger.info("created acct junction");
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end create()
	
	public AccountJunction retrieve(Integer junctionID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
						     "FROM acct_junctions " +
						     "WHERE junction_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, junctionID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				logger.info("retrieved acct junction with id" + junctionID);
				return new AccountJunction(junctionID, rs.getInt(2),
										   rs.getInt(3));
			}// end if
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		} // end try-catch
		logger.info("no acct junction found");
		return null;
	} // end retrieve()
	
	public ArrayList<AccountJunction> retrieveByAcct(Integer acctNum) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM acct_junctions " +
							 "WHERE acct_num = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, acctNum);
			ResultSet rs = st.executeQuery();
			
			ArrayList<AccountJunction> juncs 
								= new ArrayList<AccountJunction>();
			
			while(rs.next()) {
				juncs.add(new AccountJunction(rs.getInt(1), 
											  rs.getInt(2), acctNum));
			}
			logger.info("retrieved all acct junctions " +
						"for acct with id " + acctNum);
			return juncs;
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.error("no acct junctions found");
		return null;
	}
	
	public void update(AccountJunction junction) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE acct_junctions " +
							 "SET user_id = ?, acct_num = ?" +
							 "WHERE junction_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, junction.getUserID());
			st.setInt(2, junction.getAcctNum());
			st.setInt(3, junction.getJunctionID());
			st.execute();
			logger.info("updated acct junction with id " + 
						junction.getJunctionID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
			e.printStackTrace();
		} // end try-catch
		
	} // end update()
	
	public void delete(AccountJunction junction) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM acct_junctions " +
						     "WHERE acct_num = ?";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, junction.getAcctNum());
			st.execute();
			logger.info("deleted acct junction with id" + 
						junction.getJunctionID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		} // end try-catch
		
	} // end delete()
	
} // end JunctionDAO