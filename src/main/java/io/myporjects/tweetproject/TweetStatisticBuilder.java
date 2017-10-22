package io.myporjects.tweetproject;

public class TweetStatisticBuilder
{
	protected Double tweetAverage;
	protected Double likesAverage;
	protected Long likesMedian;
	protected int tweetCount;
	protected int maxNumberOfLikes;
	protected int minNumberOfLikes;
	protected Tweet mostLikedTweet;

	protected int[] likesPercentiles;
	
	public TweetStatisticBuilder()
	{
		super();
	}
	
	public TweetStatisticBuilder setTweetAverage(Double tweetAverage)
	{
		this.tweetAverage = tweetAverage;
		return this;
	}
	
	public TweetStatisticBuilder setLikesAverage(Double likesAverage)
	{
		this.likesAverage = likesAverage;
		return this;
	}
	
	public TweetStatisticBuilder setLikesMedian(Long likesMedian)
	{
		this.likesMedian = likesMedian;
		return this;
	}
	
	public TweetStatisticBuilder setLikesPercentiles(int[] likesPercentiles)
	{
		this.likesPercentiles = likesPercentiles;
		return this;
	}
	
	public TweetStatisticBuilder setTweetCount(int tweetCount)
	{
		this.tweetCount = tweetCount;
		return this;
	}
	
	public TweetStatisticBuilder setMaxNumberOfLikes(int maxNumberOfLikes)
	{
		this.maxNumberOfLikes = maxNumberOfLikes;
		return this;
	}

	public TweetStatisticBuilder setMinNumberOfLikes(int minNumberOfLikes)
	{
		this.minNumberOfLikes = minNumberOfLikes;
		return this;
	}
	
	public TweetStatisticBuilder setMostLikedTweet(Tweet mostLikedTweet)
	{
		this.mostLikedTweet = mostLikedTweet;
		return this;
	}
	
    public TweetStatistic build() 
    {	
    	return new TweetStatistic(this);
    }
}
