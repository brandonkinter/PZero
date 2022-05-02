package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.AppJunction;

public class AppJunctionDAO implements DAO<AppJunction, Integer, Void> {
	final Logger logger = LogManager.getLogger(AppJunctionDAO.class);
	
	public Void create(AppJunction appJunc) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO app_junctions (user_id, app_id) " +
						     "VALUES (?, ?);";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appJunc.getUserID());
			st.setInt(2, appJunc.getAppID());
			st.execute();
			logger.info("created app junction");
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting create");
			e.printStackTrace();
		}
		return null;
	}
	
	public AppJunction retrieve(Integer junctionID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM app_junctions " +
							 "WHERE junction_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, junctionID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				logger.info("retrieved app junction with id" + junctionID);
				return new AppJunction(junctionID, rs.getInt(2), rs.getInt(3));
			}
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.info("no app junctions found with id " + junctionID);
		return null;
	}
	
	public ArrayList<AppJunction> retrieveByApp(Integer appID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM app_junctions " +
							 "WHERE app_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appID);
			ResultSet rs = st.executeQuery();
			ArrayList<AppJunction> juncs = new ArrayList<AppJunction>();
			
			while(rs.next()) {
				juncs.add(new AppJunction(
						rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
			logger.info("retrieved all users for application " + appID);
			return juncs;
		} catch(SQLException e) {
			logger.error("SQL error while attempting retrieve");
			e.printStackTrace();
		}
		logger.info("no users found for application " + appID);
		return null;
	}
	
	public void update(AppJunction appJunc) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE applications " +
							 "SET user_id = ?, app_id = ? " +
							 "WHERE junction_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appJunc.getUserID());
			st.setInt(2, appJunc.getAppID());
			st.setInt(3, appJunc.getJunctionID());
			st.execute();
			logger.info("updated app junction with id " +
						appJunc.getJunctionID());
			
		} catch(SQLException e) {
			logger.error("SQL error while attempting update");
			e.printStackTrace();
		}
	}
	
	public void delete(AppJunction appJunc) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "DELETE FROM app_junctions " +
							 "WHERE junction_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appJunc.getJunctionID());
			st.execute();
			logger.info("deleted app junction with id " +
						appJunc.getJunctionID());
		} catch(SQLException e) {
			logger.error("SQL error while attempting delete");
			e.printStackTrace();
		}
	}
}
