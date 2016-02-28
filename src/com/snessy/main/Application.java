package com.snessy.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.snessy.auth.RedditConnector;
import com.snessy.auth.WeatherConnector;
import com.snessy.database.Database;

public class Application {

	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(new File("config.properties")));
		} catch (IOException ex) {
			System.out.println("File cannot be read");
			ex.printStackTrace();
		}
		
		RedditConnector redditConnector = new RedditConnector(props.getProperty("username"), 
				props.getProperty("password"), props.getProperty("clientId"), props.getProperty("clientSecret"));
		redditConnector.connect();
		
		Database.getInstance().setDatabaseSettings(props.getProperty("databaseUser"),
				props.getProperty("databasePass"), props.getProperty("databaseHost"), 
				props.getProperty("databaseName"));
		
		Database.getInstance().connect();
		
		WeatherConnector weatherConnector = new WeatherConnector();
	}
}
