#redditweatherbot

redditweatherbot is a simple bot written in Java which aims to provide subreddits with daily weather information. Users will
be able to send the bot a message asking it to join their subreddit and everyday the bot will create new post with the weather for the coming day.

The bot will have a safeguard feature which will only allow subreddit mods to request the bot to join OR a dev can request it
manually.

##Setup
To use this bot, you will need a reddit account and a reddit developer application. You can do this via https://www.reddit.com/prefs/apps/
Make sure that you set the app type to "script"

You will need to also have an account on http://openweathermap.org/api for getting weather information

Change `local.config.properties` to `config.properties` this file is where all your details go for your reddit account, reddit app, database and weather API key.

##Libs
The project currently uses the [Apache HTTPClient](https://hc.apache.org/httpcomponents-client-ga/) libs that allow for easy 
to use HTTP requests as well the [Java JSON](http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm) libs for parsing the response data and the [JDBC driver](https://dev.mysql.com/downloads/connector/j/) for SQL. 
These are included in the repo but I plan on moving them to something like Gradle.
