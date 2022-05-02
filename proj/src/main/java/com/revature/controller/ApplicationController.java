package com.revature.controller;

import java.util.ArrayList;

import com.revature.dao.AppJunctionDAO;
import com.revature.dao.ApplicationDAO;
import com.revature.models.AppJunction;
import com.revature.models.Application;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ApplicationController {
	ApplicationDAO appDAO;
	AppJunctionDAO appJuncDAO;
	
	public ApplicationController(Javalin app) {
		appDAO = new ApplicationDAO();
		appJuncDAO = new AppJunctionDAO();
		
		app.get("/apps/{appID}", getHandler);
		app.post("/apps", postHandler);
		app.put("/apps", putHandler);
		app.delete("/apps", deleteHandler);
	}
	
	public Handler getHandler = ctx -> {
		int id = Integer.parseInt(ctx.pathParam("appID"));
		
		Application app = appDAO.retrieve(id);
		ArrayList<AppJunction> juncs 
							= appJuncDAO.retrieveByApp(app.getAppID());
		ArrayList<Integer> userIDs = new ArrayList<Integer>();
		for(AppJunction junc : juncs)
			userIDs.add(junc.getUserID());
		
		app.setUserIDs(userIDs);
		
		ctx.json(app);
	};
	
	public Handler postHandler = ctx -> {
		Application app = ctx.bodyAsClass(Application.class);
		
		int appID = appDAO.create(app);
		
		app.setAppID(appID);
		
		for(Integer userID : app.getUserIDs())
			appJuncDAO.create(new AppJunction(userID, app.getAppID()));
		
		ctx.status(201);
	};
	
	public Handler putHandler = ctx -> {
		Application app = ctx.bodyAsClass(Application.class);
		
		appDAO.update(app);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		Application app = ctx.bodyAsClass(Application.class);
		ArrayList<AppJunction> juncs 
							= appJuncDAO.retrieveByApp(app.getAppID());
		for(AppJunction junc : juncs) {
			appJuncDAO.delete(junc);
		}
		
		appDAO.delete(app);
		
		ctx.status(200);
	};
}
