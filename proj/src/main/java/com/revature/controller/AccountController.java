package com.revature.controller;

import java.util.ArrayList;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountJunctionDAO;
import com.revature.models.Account;
import com.revature.models.AccountJunction;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController {
	AccountDAO acctDAO;
	AccountJunctionDAO acctJuncDAO;
	
	public AccountController(Javalin app) {
		acctDAO = new AccountDAO();
		acctJuncDAO = new AccountJunctionDAO();
		
		
		app.get("/accounts/{acctNum}", getHandler);
		app.post("/accounts/{balance}", postHandler);
		app.put("/accounts", putHandler);
		app.delete("accounts/{acctNum}", deleteHandler);
	}
	
	public Handler getHandler = ctx -> {
		int acctNum = Integer.parseInt(ctx.pathParam("acctNum"));
		
		Account acct = acctDAO.retrieve(acctNum);
		ArrayList<AccountJunction> juncs = acctJuncDAO.retrieveByAcct(acctNum);
		ArrayList<Integer> users = new ArrayList<Integer>();
		
		for(AccountJunction junc : juncs) {
			users.add(junc.getUserID());
		}
		
		ctx.json(acct);
		ctx.json(users);
	};
	
	@SuppressWarnings("unchecked")
	public Handler postHandler = ctx -> {
		ArrayList<Integer> users = ctx.bodyAsClass(ArrayList.class);
		long balance = Long.parseLong(ctx.pathParam("balance"));
		Account acct = new Account(balance);
		
		int acctNum = acctDAO.create(acct);
		
		for(Integer user : users)
			acctJuncDAO.create(new AccountJunction(user, acctNum));
		
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		Account acct = ctx.bodyAsClass(Account.class);
		
		acctDAO.update(acct);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		int acctNum = Integer.parseInt(ctx.pathParam("acctNum"));
		
		Account acct = acctDAO.retrieve(acctNum);
		
		ArrayList<AccountJunction> juncs = acctJuncDAO.retrieveByAcct(acctNum);
		
		for(AccountJunction junc : juncs)
			acctJuncDAO.delete(junc);
		
		acctDAO.delete(acct);
		
	};
}
