package com.revature.controller;

import com.revature.dao.AccountDAO;
import com.revature.models.Account;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController {
	AccountDAO acctDAO;
	
	public AccountController(Javalin app) {
		
		acctDAO = new AccountDAO();
		
		app.get("/accounts/{acctNum}", getHandler);
		app.post("/accounts", postHandler);
		app.put("/accounts/{acctNum}", putHandler);
		app.delete("/accounts/{acctNum}", deleteHandler);
	}
	
	public Handler getHandler = ctx -> {
		String acctNum = ctx.pathParam("acctNum");
		
		Account acct = acctDAO.retrieve(Integer.parseInt(acctNum));
	
		ctx.json(acct);
	};
	
	public Handler postHandler = ctx -> {
		Account acct = ctx.bodyAsClass(Account.class);
		
		acctDAO.create(acct);
		
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		Account acct = ctx.bodyAsClass(Account.class);
		
		acctDAO.update(acct);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		Account acct = ctx.bodyAsClass(Account.class);
		
		acctDAO.delete(acct);
		
		ctx.status(200);
	};
}
