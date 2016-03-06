package com.snessy.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.snessy.auth.HttpHandler;
import com.snessy.auth.RedditConnector;
import com.snessy.auth.WeatherConnector;
import com.snessy.dao.SubredditDao;
import com.snessy.database.Database;
import com.snessy.parse.RedditJsonParser;
import com.snessy.reddit.Subreddit;

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
				props.getProperty("databaseName"), props.getProperty("databasePort"));
		
		Database.getInstance().connect();
		
		WeatherConnector weatherConnector = new WeatherConnector(props.getProperty("weatherAPIKey"));
		SubredditDao subredditDao = new SubredditDao();
		
		Timer timer = new Timer();
		RedditJsonParser jsonParser = new RedditJsonParser();
		
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				Subreddit[] subreddits = subredditDao.getAllEntries();
				
				if(subreddits != null) {
					HashMap<String, String> headers = new HashMap<String, String>();
					HashMap<String, String> urlNameValuePairs = new HashMap<String, String>();
					
					headers.put("authorization", "bearer "+redditConnector.getAccessToken());
					headers.put("user-agent", RedditConnector.USER_AGENT);
					
					urlNameValuePairs.put("api_type", "json");
					urlNameValuePairs.put("kind", "self");
					urlNameValuePairs.put("sr", "codingresources");
					urlNameValuePairs.put("sendreplies", "true");
					urlNameValuePairs.put("text", "Test Post");
					urlNameValuePairs.put("title", "TestPost");
					urlNameValuePairs.put("url", "text");
					
					for(Subreddit sub : subreddits){
						headers.put("sr", sub.getName());
						CloseableHttpResponse response = HttpHandler.post("https://oauth.reddit"
								+ ".com/api/submit", null, null, headers, urlNameValuePairs);
						try {
							JSONObject json = jsonParser.parseData(EntityUtils.toString(response.getEntity()));
							System.out.println(json);
						} catch (ParseException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, 0 ,TimeUnit.HOURS.toMillis(24));
		
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// Code to check messages and update database with new requests
			}
			
		}, 0, TimeUnit.MINUTES.toMillis(5));
	}
}
