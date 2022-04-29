package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import com.revature.models.Application;

public class ApplicationDAO implements DAO<Application, Integer, Integer> {
	
	public Integer create(Application app) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO applications (income, deposit) " +
							 "VALUES (?, ?) " +
							 "RETURNING app_id;";
			PreparedStatement st = c.prepareStatement(command);
			st.setLong(1, app.getIncome());
			st.setLong(2,  app.getDeposit());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch(SQLException e) {
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
				return new Application(appID, rs.getLong(2), rs.getLong(3));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Queue<Application> retrieveAll() {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT app_id, user_id, income, deposit " +
							 "FROM applications " +
							 "INNER JOIN app_junctions " +
							 "USING (app_id) " +
							 "ORDER BY app_id ASC;";
			PreparedStatement st = c.prepareStatement(command);
			ResultSet rs = st.executeQuery();
			Queue<Application> apps = new LinkedList<Application>();
			while(rs.next()) {
				apps.add(new Application(
							rs.getInt(1), rs.getInt(2), 
							rs.getLong(3), rs.getLong(4)));
			}
			return apps;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void update(Application app) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "UPDATE applications " +
							 "SET income = ? " + 
							 "WHERE app_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setLong(1, app.getIncome());
			st.setInt(2, app.getAppID());
			st.execute();
			
		} catch(SQLException e) {
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
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
