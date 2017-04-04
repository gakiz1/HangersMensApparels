package com.hangers.services;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;


import com.hangers.data.DatabaseServices;
import com.hangers.pojo.Item;

@Path("/rest")
public class TestService {
	@GET
	@Path("/ping")
	public String ping() {
		return "working fine...";
	}

	@POST
	@Path("/addStock")
	public String insertNewItem(Item item) {
		try {
			
			if(item==null){
				
				return "item is null";
			}else{
				String message=DatabaseServices.addStock(item);
				return 	message;
			}
			
			
		} catch (Exception e) {
			System.out.println("JSONException : " + e);
			return "Item code already exist..";
		} 
	}
	
	@POST
	@Path("/sell")
	public String sellItem(Item item) {
		try {
			
			if(item==null){
				
				return "item is null";
			}else{
				String message=DatabaseServices.sell(item);
				return 	message;
			}
			
			
		} catch (Exception e) {
			System.out.println("JSONException : " + e);
			return "This Item is not there in Stock..Please check the item code..";
		} 
	}
	
	@POST
	@Path("/accounts")
	public float accounts(Item item) {
		try {
			
			if(item==null){
				
				return 0;
			}else{
				return DatabaseServices.accounts(item);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("JSONException : " + e);
			return 0;
		} 
	}
	@GET
	@Path("/getAll")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllItems(){
		String result = null;
		try {
			result = DatabaseServices.getAllItems().toString();
		} catch (ClassNotFoundException | URISyntaxException | SQLException
				| JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GET
	@Path("/sold")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllItemsFromDB(){
		String result = null;
		try {
			result = DatabaseServices.getAllItemsSold().toString();
		} catch (ClassNotFoundException | URISyntaxException | SQLException
				| JSONException e) {
			result = e.toString();
			e.printStackTrace();
		}
		return result;
	}
	

	@GET
	@Path("/dropTable")
	public String createTable(){
		try{
		return DatabaseServices.CreateTable();
	}catch(Exception e){
		return 
				"Failed : "+e;
	}
	}
	
	
}
