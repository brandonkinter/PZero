package com.revature.controller;

import com.revature.dao.LoginDAO;
import com.revature.dao.PersonalInfoDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Login;
import com.revature.models.PersonalInfo;
import com.revature.models.User;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController {
	LoginDAO loginDAO;
	UserDAO userDAO;
	PersonalInfoDAO infoDAO;
	
	public UserController(Javalin app) {
		
		userDAO = new UserDAO();
		infoDAO = new PersonalInfoDAO();
		loginDAO = new LoginDAO();
		
		app.get("/users/{username}", getHandler);
		app.post("/users/{username}/{role}", postHandler);
		app.put("/users", putHandler);
		app.delete("/users/{username}", deleteHandler);
	}
	
	public Handler getHandler = ctx -> {
		String username = ctx.pathParam("username");
		
		Login login = loginDAO.retrieve(username);
		
		PersonalInfo info = infoDAO.retrieve(login.getUserID());
	
		ctx.json(info);
	};
	
	public Handler postHandler = ctx -> {
		String username = ctx.pathParam("username");
		String role = ctx.pathParam("role");
		PersonalInfo info = ctx.bodyAsClass(PersonalInfo.class);
		Login login = new Login(username, "P@ssw0rd");
	
		int userID = loginDAO.create(login);
		
		User user = null;
		
		switch(role) {
			case "customer":
				user = new Customer(userID);
			case "employee":
				user = new Employee(userID);
			case "admin":
				user = new Admin(userID);
		}
		
		userDAO.create(user);
		infoDAO.create(info);
		
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		PersonalInfo info = ctx.bodyAsClass(PersonalInfo.class);
		
		infoDAO.update(info);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		String username = ctx.pathParam("username");
		
		PersonalInfo info = ctx.bodyAsClass(PersonalInfo.class);
		Login login = loginDAO.retrieve(username);
		User user = userDAO.retrieve(login.getUserID());
		
		infoDAO.delete(info);
		userDAO.delete(user);
		loginDAO.delete(login);
		
		ctx.status(200);
	};
}
