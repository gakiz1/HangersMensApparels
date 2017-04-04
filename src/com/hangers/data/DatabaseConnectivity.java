package com.hangers.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {
	
	private static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
	public static Connection getConnected() throws URISyntaxException, SQLException, ClassNotFoundException{
		return getConnection();
	}

}
