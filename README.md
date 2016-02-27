# redditbot

A simple bot for reddit written in Java!

There are currently no plans for what this bot can/will do. At the moment the bot will get an access token and then get json data about
the user account.

##Setup
To use this bot, you will need a reddit account and a reddit developer application. You can do this via https://www.reddit.com/prefs/apps/
Make sure that you set the app type to "script"

Change `local.config.properties` to `config.properties` this is where your reddit username, reddit password, 
app client id and app client secret should go.

##Libs
The project currently uses the [Apache HTTPClient](https://hc.apache.org/httpcomponents-client-ga/) libs that allow for easy 
to use HTTP requests as well the [Java JSON](http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm) libs for parsing the response data. 
These are included in the repo but I plan on moving them to something like Gradle.
