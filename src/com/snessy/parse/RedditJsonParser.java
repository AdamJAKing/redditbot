package com.snessy.parse;

import org.json.JSONException;
import org.json.JSONObject;

public class RedditJsonParser {
	
	public JSONObject parseData(String json) {
		JSONObject parsedJson = null;
		try {
			parsedJson = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return parsedJson;
	}
}
