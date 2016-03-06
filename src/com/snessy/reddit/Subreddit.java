package com.snessy.reddit;

public class Subreddit {
	
	private int id;
	private String name;
	private int locationId;
	
	public Subreddit(int id, String name, int locationId) {
		this.id = id;
		this.name = name;
		this.locationId = locationId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLocationId() {
		return locationId;
	}
}
