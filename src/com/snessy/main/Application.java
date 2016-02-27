package com.snessy.main;
import java.io.IOException;

import com.snessy.auth.RedditConnector;

public class Application {

	public static void main(String[] args) throws IOException {
		RedditConnector redditConnector = new RedditConnector();
		redditConnector.connect("config.properties");
	}
}
