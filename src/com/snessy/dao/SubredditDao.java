package com.snessy.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.snessy.database.Database;
import com.snessy.reddit.Subreddit;

public class SubredditDao {
	
	public Subreddit[] getAllEntries(){
		try {
			ResultSet rs = Database.getInstance().getConnection()
					.prepareStatement("SELECT * FROM subreddits").executeQuery();
			
			if(rs.last()){
				Subreddit[] subreddits = new Subreddit[rs.getRow()];
				rs.beforeFirst();
				
				while(rs.next()){
					subreddits[rs.getRow()-1] = new Subreddit(rs.getInt("id"), rs.getString("name"),
							rs.getInt("location_id"));
				}
				return subreddits;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
