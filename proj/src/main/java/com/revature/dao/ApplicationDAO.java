package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Application;

public class ApplicationDAO implements DAO<Application, Integer, Integer> {
	final Logger logger = LogManager.getLogger(ApplicationDAO.class);
	
	public Integer create(Application app) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO applications " +
										"(income, deposit, is_open) " +
							 "VALUES (?, ?, TRUE) " +
							 "RETURNING app_id;";
			PreparedStatement st = c.prepareStatement(command);
			st.setLong(1, app.getIncome());
			st.setLong(2,  app.getDeposit());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt(1);
				logger.info("created application with id " + id);
				return id;
			}
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
			e.printStackTrace();
		}
		return null;
	}
	
	public Application retrieve(Integer appID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM applications " +
							 "WHERE app_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				logger.info("retrieved application with id " + appID);
				return new Application(appID, rs.getLong(2), rs.getLong(3), 
									   rs.getBoolean(4));
			}
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.error("no application found with id " + appID);
		return null;
	}
	
	public Queue<Application> retrieveByCust(Integer userID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM applications " +
							 "WHERE user_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			Queue<Application> result = new LinkedList<Application>();
			
			while(rs.next()) {
				result.add(new Application(rs.getInt(1), rs.getLong(2), 
									       rs.getLong(3), rs.getBoolean(4)));
			}
			logger.info("retrieved all applications for customer " + userID);
			return result;
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.error("no applications found for customer " + userID);
		return null;
	}
	
	public Queue<Application> retrieveAllOpen() {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " + 
							 "FROM applications " +
							 "WHERE is_open = TRUE " +
							 "ORDER BY app_id ASC;";
			PreparedStatement st = c.prepareStatement(command);
			ResultSet rs = st.executeQuery();
			Queue<Application> apps = new LinkedList<Application>();
			while(rs.next()) {
				apps.add(new Application(rs.getInt(1), rs.getLong(2), 
									     rs.getLong(3), rs.getBoolean(4)));
			}
			logger.info("retrieved all open applications");
			return apps;
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.info("no open applications found");
		return null;
	}
	
	public Queue<Application> retrieveAllClosed() {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " + 
							 "FROM applications " +
							 "WHERE is_open = FALSE " +
							 "ORDER BY app_id ASC;";
			PreparedStatement st = c.prepareStatement(command);
			ResultSet rs = st.executeQuery();
			Queue<Application> apps = new LinkedList<Application>();
			while(rs.next()) {
				apps.add(new Application(rs.getInt(1), rs.getLong(2),
										 rs.getLong(3), rs.getBoolean(4)));
			}
			logger.info("retrieved all closed applications");
			return apps;
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.info("no closed applications found");
		return null;
	}
	
	public Queue<Application> retrieveAll() {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM applications " +
							 "ORDER BY app_id ASC;";
			PreparedStatement st = c.prepareStatement(command);
			ResultSet rs = st.executeQuery();
			Queue<Application> apps = new LinkedList<Application>();
			while(rs.next()) {
				apps.add(new Application(rs.getInt(1), rs.getLong(2), 
										 rs.getLong(3), rs.getBoolean(4)));
			}
			logger.info("retrieved all applications");
			return apps;
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.info("no applications found");
		return null;
	}
	
	public void update(Application app) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE applications " +
							 "SET is_open = ? " + 
							 "WHERE app_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setBoolean(1, app.getIsOpen());
			st.setInt(2, app.getAppID());
			st.execute();
			logger.info("updated application with id " + app.getAppID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
			e.printStackTrace();
		}
	}
	
	public void delete(Application app) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM applications " +
							 "WHERE app_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1,  app.getAppID());
			st.execute();
			logger.info("deleted application with id " + app.getAppID());
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		}
	}
}
