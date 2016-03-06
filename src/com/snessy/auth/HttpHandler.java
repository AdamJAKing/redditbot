package com.snessy.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author Aking
 * 
 * Utility class used for sending POST/GET requests
 */
public class HttpHandler {
	
	/**
	 * 
	 * @param url The URL to connect to
	 * @param clientId Client ID. Used for basic credentials
	 * @param clientSecret Client Secret. Used for basic credentials
	 * @param headers Headers to send with the request
	 * @param nameValuePairs Additional URL parameters to send via the post execution 
	 * @return Browser response object or null if the request failed
	 */
	public static CloseableHttpResponse post(String url, String clientId, String clientSecret, HashMap<String, String>headers, HashMap<String, String> nameValuePairs) {
		CloseableHttpClient client;
		ArrayList<NameValuePair> entityPairs = new ArrayList<NameValuePair>();
		
		if(clientId != null && clientSecret != null) {
			CredentialsProvider credentials = new BasicCredentialsProvider();
			credentials.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(clientId, clientSecret));
			client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentials).build();
		}else {
			client = HttpClients.createDefault();
		}
		
		HttpPost post = new HttpPost(url);
		
		if(headers != null)
			for(Map.Entry<String, String> entry : headers.entrySet()) {
			post.addHeader(entry.getKey(), entry.getValue());
		}
		
		if(nameValuePairs != null) {
			for(Map.Entry<String, String> entry : nameValuePairs.entrySet()) {
				entityPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		CloseableHttpResponse response = null;
		
		try {
			post.setEntity(new UrlEncodedFormEntity(entityPairs));
			response = client.execute(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static CloseableHttpResponse get(String url, HashMap<String, String>headers) {
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet get = new HttpGet(url);
		
		if(headers != null) {
			for(Map.Entry<String, String> entry : headers.entrySet()) {
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (IOException e) {
			System.out.println("Failed to read url");
			e.printStackTrace();
		}
		return response ;
	}
}
