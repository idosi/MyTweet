package io.myprojects.tweetproject;

public class TweetStatisticBuilder {
	protected double sumOfLikes;
	protected double likesAverage;
	protected long likesMedian;
	protected int tweetCount;
	protected int maxNumberOfLikes;
	protected int minNumberOfLikes;
	protected Tweet mostLikedTweet;

	protected int[] likesPercentiles;
	
	public TweetStatisticBuilder() { super(); }
	
	public TweetStatisticBuilder setSumOfLikes(double sumOfLikes) {
		this.sumOfLikes = sumOfLikes;
		return this;
	}
	
	public TweetStatisticBuilder setLikesAverage(double likesAverage) {
		this.likesAverage = likesAverage;
		return this;
	}
	
	public TweetStatisticBuilder setLikesMedian(long likesMedian) {
		this.likesMedian = likesMedian;
		return this;
	}
	
	public TweetStatisticBuilder setLikesPercentiles(int[] likesPercentiles) {
		this.likesPercentiles = likesPercentiles;
		return this;
	}
	
	public TweetStatisticBuilder setTweetCount(int tweetCount) {
		this.tweetCount = tweetCount;
		return this;
	}
	
	public TweetStatisticBuilder setMaxNumberOfLikes(int maxNumberOfLikes) {
		this.maxNumberOfLikes = maxNumberOfLikes;
		return this;
	}

	public TweetStatisticBuilder setMinNumberOfLikes(int minNumberOfLikes) {
		this.minNumberOfLikes = minNumberOfLikes;
		return this;
	}
	
	public TweetStatisticBuilder setMostLikedTweet(Tweet mostLikedTweet) {
		this.mostLikedTweet = mostLikedTweet;
		return this;
	}
	
    public TweetStatistic build() {	return new TweetStatistic(this); }
}
