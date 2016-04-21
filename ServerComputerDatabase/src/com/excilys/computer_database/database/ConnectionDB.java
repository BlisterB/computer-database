package com.excilys.computer_database.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	private static final String
		url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull",
		user = "admincdb",
		passwd = "qwerty1234";
	
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(url, user, passwd);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
