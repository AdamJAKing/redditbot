package com.snessy.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.snessy.parse.RedditJsonParser;

public class RedditConnector {
	
	public static final String USER_AGENT = "Snessabot-redditbotv1.0 by /u/Ihuntbacon";
	
	/**
	 * Loads in the properties file and uses the data to connect the the reddit API
	 * 
	 * @param filePath Path to the properties file
	 */
	
	public void connect(String filePath) {
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(new File(filePath)));
		} catch (IOException ex) {
			System.out.println("File: " + filePath + " cannot be read");
			ex.printStackTrace();
		}
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", USER_AGENT);
		
		HashMap<String, String> nameValuePairs = new HashMap<String, String>();
		nameValuePairs.put("grant_type", "password");
		nameValuePairs.put("username", props.getProperty("username"));
		nameValuePairs.put("password", props.getProperty("password"));
		
		CloseableHttpResponse response = HttpHandler.post("https://www.reddit.com/api/v1/access_token", props.getProperty("clientId"), 
				props.getProperty("clientSecret"), headers, nameValuePairs);
		
		RedditJsonParser rjp = new RedditJsonParser();
		
		try {
			JSONObject parsedJson = rjp.parseData(EntityUtils.toString(response.getEntity()));
			headers.put("Authorization","bearer " + parsedJson.getString("access_token"));
			CloseableHttpResponse accessTokenResponse =  HttpHandler.get("https://oauth.reddit.com/api/v1/me", headers);
			
			System.out.println(EntityUtils.toString(accessTokenResponse.getEntity()));
		} catch (ParseException | IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
