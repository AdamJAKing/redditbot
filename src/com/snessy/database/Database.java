package com.snessy.database;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author AKing
 *
 * Database singleton class
 */
public class Database {
	
	private static Database instance = new Database();
	
	private String user;
	private String password;
	private String host;
	private String port;
	private String database;
	private boolean settingsSet = false;
	
	private Database() {
		
	}
	
	public static Database getInstance(){
		return instance;
	}
	
	/**
	 * 
	 * @param user SQL username
	 * @param password SQL password
 	 * @param host SQL host
	 * @param database SQL database
	 * @param port SQL port
	 */
	public void setDatabaseSettings(String user, String password, String host, String database, String port){
		this.user = user;
		this.password = password;
		this.host = host;
		this.database = database;
		this.port = port;
		
		settingsSet = true;
	}
	
	/**
	 * Checks if the database settings are set and if so, attempts to connect to the database
	 */
	public void connect() {
		
		if(settingsSet){
			System.out.println("Attempting to connect to database...");
			try {
				DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, user, password);
				System.out.println("Connected!");
			} catch (SQLException e) {
				System.out.println("Failed to connect to database!");
				e.printStackTrace();
			}
		} else {
			System.out.println("Call setDatabaseSettings() before attempting to connect to the database!");
		}
	}
}
