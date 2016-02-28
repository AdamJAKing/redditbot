package com.snessy.auth;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.snessy.parse.RedditJsonParser;

public class RedditConnector {
	
	public static final String USER_AGENT = "Snessabot-redditbotv1.0 by /u/Ihuntbacon";
	
	private String username;
	private String password;
	private String clientId;
	private String clientSecret;
	private String accessToken;
	
	public RedditConnector(String user, String pass, String clientId, String clientSecret) {
		this.username = user;
		this.password = pass;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	/**
	 * Loads in the properties file and uses the data to connect the the reddit API
	 * 
	 * @param filePath Path to the properties file
	 */
	
	public void connect() {
		System.out.println("Attemping to connect to reddit...");
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", USER_AGENT);
		
		HashMap<String, String> nameValuePairs = new HashMap<String, String>();
		nameValuePairs.put("grant_type", "password");
		nameValuePairs.put("username", username);
		nameValuePairs.put("password", password);
		
		CloseableHttpResponse response = HttpHandler.post("https://www.reddit.com/api/v1/access_token", clientId, 
				clientSecret, headers, nameValuePairs);
		
		/*
		 * If the status code is okay, try and parse the json and get user information about the account
		 */
		if(response.getStatusLine().getStatusCode() == 200) {
			System.out.println("Connected!");
		
			RedditJsonParser rjp = new RedditJsonParser();
			
			try {
				JSONObject parsedJson = rjp.parseData(EntityUtils.toString(response.getEntity()));
				headers.put("Authorization","bearer " + parsedJson.getString("access_token"));
				CloseableHttpResponse accessTokenResponse =  HttpHandler.get("https://oauth.reddit.com/api/v1/me", headers);
				accessToken = parsedJson.getString("access_token");
				
			} catch (ParseException | IOException | JSONException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to connect to reddit\n" + response.getStatusLine());
		}
	}
}
