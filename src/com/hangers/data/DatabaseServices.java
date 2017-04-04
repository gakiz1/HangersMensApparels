package com.hangers.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.hangers.pojo.Item;


public class DatabaseServices {
	private final static String ADD_STOCK_QUERY="INSERT INTO STOCKIN VALUES(?,?,?,?,?,?,?)";
	private final static String QUANTITY_QUERY="SELECT QUANTITY FROM STOCKIN WHERE ITEM_CODE=?";
	private final static String ADD_STOCK_TO_MASTER_QUERY="INSERT INTO MASTER VALUES(?,?,?,?,?,?,?)";
	private static String dropQuery = "DROP table STOCKOUT";
	private static String tableCreate = "CREATE TABLE STOCKOUT(ITEM_CODE CHAR(50) NOT NULL,QUANTITY INT NOT NULL,PRICE_OUT FLOAT(20) NOT NULL,DATE_OUT DATE NOT NULL,TRANSACTION_ID CHAR(50)) ";
	private static String truncate="TRUNCATE TABLE MASTER";
	private final static String DECREMENT_QUANTITY_QUERY ="UPDATE STOCKIN SET QUANTITY=QUANTITY - ? WHERE ITEM_CODE=? ";
	private final static String ADD_SELL_QUERY="INSERT INTO STOCKOUT VALUES(?,?,?,?,?)";
	private final static String ACCOUNTS_QUERY1=
	"SELECT (SOUT.QUANTITY * SOUT.PRICE_OUT)-(SIN.PRICE_IN * SOUT.QUANTITY) FROM STOCKIN SIN , STOCKOUT SOUT WHERE SOUT.ITEM_CODE = SIN.ITEM_CODE AND SOUT.DATE_OUT  BETWEEN ? AND ?";
	
	
	public static String CreateTable()throws ClassNotFoundException, URISyntaxException, SQLException {
		
		Connection connection = DatabaseConnectivity.getConnected();
		if (connection != null) {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(truncate);
			String result=rs.toString();
			System.out.println("Query executed!");
			connection.close();
			return "DB : "+result;
		}else{
			return "Failed to connect to db";
	    }
	}
	public static String addStock(Item item)
			throws ClassNotFoundException, URISyntaxException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result;
		Connection connection = DatabaseConnectivity.getConnected();
		if (connection != null) {
			Statement st = connection.createStatement();
            String sqlQuery = ADD_STOCK_QUERY;
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getItemType());
            preparedStatement.setString(3, item.getBrand());
            preparedStatement.setInt(4, item.getQuantity());
            preparedStatement.setString(5, item.getSize());
            preparedStatement.setFloat(6, item.getPriceIn());
            preparedStatement.setDate(7, item.getDateIn());
            
            int rs = preparedStatement.executeUpdate();
            
            String sqlQuery2 = ADD_STOCK_TO_MASTER_QUERY;
            preparedStatement = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getItemType());
            preparedStatement.setString(3, item.getBrand());
            preparedStatement.setInt(4, item.getQuantity());
            preparedStatement.setString(5, item.getSize());
            preparedStatement.setFloat(6, item.getPriceIn());
            preparedStatement.setDate(7, item.getDateIn());
            
            int ms =preparedStatement.executeUpdate();
            if (rs != 0 && ms != 0) {
            	System.out.println("successfull!");
            	 result="successfully Added the stock";
              
            } else {
	            System.out.println("Failed!");
	             result="Failed.. ";
            }

			
			connection.close();
			return result;
		}
		else
			return "Connection Failed";

	}
	
	public static float accounts(Item item)
			throws ClassNotFoundException, URISyntaxException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result;
        List<Float> profit = new ArrayList();
        float sum =0F;
        
		Connection connection = DatabaseConnectivity.getConnected();
		if (connection != null) {
			Statement st = connection.createStatement();
            String sqlQuery = ACCOUNTS_QUERY1;
            
    
            preparedStatement = connection.prepareStatement(sqlQuery);
            java.sql.Date one=item.getDateIn();
            java.sql.Date two=item.getDateOut();
            if(one.after(two)){
            	java.sql.Date temp=two;
            	two=one;
            	one=temp;
            	
            }
            
          preparedStatement.setDate(1, one);
            preparedStatement.setDate(2, two);
            ResultSet rs = preparedStatement.executeQuery();
           
            while (rs.next()) {
            	System.out.println("successfull!");
            	 result="successfully Sold...";
            	sum+=rs.getFloat(1);
            	 profit.add(rs.getFloat(1));
            } 

			
			connection.close();
			return sum;
		}
		else
			return sum;

	}

	public static String sell(Item item)
			throws ClassNotFoundException, URISyntaxException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result;
        String results="default";
        int qty=0;
		Connection connection = DatabaseConnectivity.getConnected();
		if (connection != null) {
			Statement st = connection.createStatement();
			
			String sqlQuery=QUANTITY_QUERY;
			preparedStatement = connection.prepareStatement(sqlQuery);
        	 preparedStatement.setString(1, item.getItemCode());
             ResultSet rsq = preparedStatement.executeQuery();
             
             while(rsq.next()){
            	 
            	qty= rsq.getInt(1);
             }
			if(qty >= item.getQuantity()){
             sqlQuery = DECREMENT_QUANTITY_QUERY;
            
    
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, item.getQuantity());
            preparedStatement.setString(2, item.getItemCode());
            int rs = preparedStatement.executeUpdate();
           
            if (rs != 0) {
            	System.out.println("successfull!");
            	 result="successfully Sold...";
            	
            	sqlQuery =ADD_SELL_QUERY; 
            	
            	Date date= new Date();
                String transactionId="H"+date;
                
            	preparedStatement = connection.prepareStatement(sqlQuery);
               
                preparedStatement.setString(1, item.getItemCode());
                preparedStatement.setInt(2, item.getQuantity());
                preparedStatement.setFloat(3, item.getPriceOut());
                preparedStatement.setDate(4, item.getDateOut());
                preparedStatement.setString(5, transactionId);
                
               
                
                int rst = preparedStatement.executeUpdate();
                
                if(rst != 0){
                 results="Successfully added to the Stock Out table";
                }else{
                	results="Failed to add to the Stock out table ";
                }
            	
              
            } else {
	            System.out.println("Failed!");
	             result="Failed to sell ";
            }

			
			connection.close();
			return result+results;
			}
			else{
				return "The Item is not in Stock";
			}
		}
		else
			return "Connection Failed";

	}
	
	public static JSONArray getAllItems() throws ClassNotFoundException, URISyntaxException, SQLException, JSONException{
		Connection connection = DatabaseConnectivity.getConnected();
		JSONArray jsonArray = new JSONArray();
		if (connection != null) {
			Statement st = connection.createStatement();
			String query = "SELECT * FROM STOCKIN";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				JSONObject jsonObject = new JSONObject();
				
				/*jsonObject.put("item code", rs.getString(1));
				
				jsonObject.put("quantity", rs.getInt(2));
				
				jsonObject.put("price", rs.getFloat(3));
				jsonObject.put("date Out", rs.getDate(4));
				jsonObject.put("t Id", rs.getString(5));*/
				
                jsonObject.put("itemCode", rs.getString(1));
                jsonObject.put("itemType", rs.getString(2));
                jsonObject.put("brand", rs.getString(3));
				jsonObject.put("quantity", rs.getInt(4));
				jsonObject.put("size", rs.getString(5));
				jsonObject.put("price", rs.getFloat(6));
				jsonObject.put("dateIn", rs.getDate(7));
				jsonArray.put(jsonObject);
			}
		}
		connection.close();
		return jsonArray;
	}
	
	public static JSONArray getAllItemsSold() throws ClassNotFoundException, URISyntaxException, SQLException, JSONException{
		Connection connection = DatabaseConnectivity.getConnected();
		JSONArray jsonArray = new JSONArray();
		if (connection != null) {
			Statement st = connection.createStatement();
			String query = "SELECT * FROM STOCKOUT";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("itemCode", rs.getString(1));
				jsonObject.put("quantity", rs.getInt(2));
				jsonObject.put("price", rs.getFloat(3));
				jsonObject.put("dateOut", rs.getDate(4));
				jsonObject.put("transactionId", rs.getString(5));
				jsonArray.put(jsonObject);
			}
		}
		connection.close();
		return jsonArray;
	}

}
