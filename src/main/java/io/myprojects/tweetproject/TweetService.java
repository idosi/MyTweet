package io.myprojects.tweetproject;

import java.util.Collection;

public interface TweetService {
    int getTweetCount();

    Tweet getTweet(int tweetId);

    void addTweet(Tweet tweet);

    void addTweets(Collection<Tweet> tweets);

	TweetStatistic getStats();

	TweetStatistic getStats(int userId);

	void clear();
}
