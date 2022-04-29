package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.AppJunction;

public class AppJunctionDAO implements DAO<AppJunction, Integer, Void> {
	public Void create(AppJunction appJunc) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "INSERT INTO app_junctions (user_id, app_id) " +
						     "VALUES (?, ?);";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appJunc.getUserID());
			st.setInt(2, appJunc.getAppID());
			st.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AppJunction retrieve(Integer appID) {
		Connection c = ConnectionManager.getConnection();
		
		try {
			String command = "SELECT * " +
							 "FROM app_junctions " +
							 "WHERE app_id = ?;";
			PreparedStatement st = c.prepareStatement(command);
			st.setInt(1, appID);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				return new AppJunction(rs.getInt(1), rs.getInt(2), appID);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
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
			
		} catch(SQLException e) {
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
