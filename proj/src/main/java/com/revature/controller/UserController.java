package com.revature.controller;

import com.revature.dao.UserDAO;
import com.revature.models.Customer;
import com.revature.models.User;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController {
	UserDAO userDAO;
	
	public UserController(Javalin app) {
		userDAO = new UserDAO();
		
		app.get("/users/{userID}", getHandler);
		app.post("/users", postHandler);
		app.put("/users/{username}", putHandler);
		app.delete("/users/{username}", deleteHandler);
	}
	
	public Handler getHandler = ctx -> {
		String id = ctx.pathParam("userID");
		
		User user = userDAO.retrieve(Integer.parseInt(id));
		
		ctx.json(user);
	};
	
	public Handler postHandler = ctx -> {
		Customer user = ctx.bodyAsClass(Customer.class);
		
		userDAO.create(user);
		
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		User user = ctx.bodyAsClass(User.class);
		
		userDAO.update(user);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		Customer user = ctx.bodyAsClass(Customer.class);
		
		userDAO.delete(user);
		
		ctx.status(200);
	};
}
