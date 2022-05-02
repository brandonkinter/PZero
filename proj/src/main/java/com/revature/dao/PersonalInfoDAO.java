package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.PersonalInfo;

public class PersonalInfoDAO implements DAO<PersonalInfo, Integer, Void> {
	final Logger logger = LogManager.getLogger(PersonalInfoDAO.class);
	
	public Void create(PersonalInfo info) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO personal_info " +
							 "VALUES (?, ?, ?, ?);";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, info.getUserID());
			st.setString(2, info.getFirstName());
			st.setString(3, info.getLastName());
			st.setLong(4, info.getPhoneNum());
			st.execute();
			logger.info("created info entry for user with id " + 
						 info.getUserID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
			e.printStackTrace();
		} // end try-catch
		
		return null;
	} // end create()
	
	public PersonalInfo retrieve(Integer userID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM personal_info " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				logger.info("retrieved info for user with id " + userID);
				return new PersonalInfo(rs.getInt(1), rs.getString(2),
									    rs.getString(3), rs.getLong(4));
			}
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.error("no info found for user with id " + userID);
		return null;
	} // end retrieve()
	
	public void update(PersonalInfo info) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE personal_info " +
							 "SET phone_num = ? " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setLong(1, info.getPhoneNum());
			st.setInt(2, info.getUserID());
			st.execute();
			
			logger.info("successfully updated info for user with id " + 
						 info.getUserID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
			e.printStackTrace();
		} // end try-catch
		
	} // end update()
	
	public void delete(PersonalInfo info) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM personal_info " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, info.getUserID());
			st.execute();
			
			logger.info("successfully deleted info for user with id " +
						 info.getUserID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		}
		
	} // end delete()
}
