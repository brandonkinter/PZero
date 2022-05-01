package com.revature.controller;

import com.revature.dao.LoginDAO;
import com.revature.models.Login;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController {
	LoginDAO loginDAO;
	
	public LoginController(Javalin app) {
		loginDAO = new LoginDAO();
		
		app.get("/logins/{username}", getHandler);
		app.post("/logins", postHandler);
		app.put("/users/{username}", putHandler);
		app.delete("/users/{username}", deleteHandler);
	}
	
	public Handler getHandler = ctx -> {
		String username = ctx.pathParam("username");
		
		Login login = loginDAO.retrieve(username);
		
		ctx.json(login);
	};
	
	public Handler postHandler = ctx -> {
		Login login = ctx.bodyAsClass(Login.class);
		
		loginDAO.create(login);
		
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		Login login = ctx.bodyAsClass(Login.class);
		
		loginDAO.update(login);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		Login login = ctx.bodyAsClass(Login.class);
		
		loginDAO.delete(login);
		
		ctx.status(200);
	};
}
