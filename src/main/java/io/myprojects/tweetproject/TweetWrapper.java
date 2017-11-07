package io.myprojects.tweetproject;

import java.util.Collection;

public class TweetWrapper {
	private Collection<Tweet> tweets;
	private TweetWrapper(){}
	
	public Collection<Tweet> getTweets() { return tweets; }
	public void setTweets(Collection<Tweet> tweets) { this.tweets = tweets; }
}
