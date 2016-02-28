package com.snessy.auth;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherConnector {
	
	public JSONObject connect(String location, String country) {
		CloseableHttpResponse response = HttpHandler.get("http://api.openweathermap.org"
				+ "/data/2.5/weather?q="+location+","+country+"&"+ "appid=44db6a862fba0b067b1930da0d769e98", null);
		try {
			return new JSONObject(EntityUtils.toString(response.getEntity()));
		} catch (ParseException | IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
