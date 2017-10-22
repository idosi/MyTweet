package io.myporjects.tweetproject;

public class TweetStatistic
{
	private final Double tweetAverage;
	private final Double likesAverage;
	private final Long likesMedian;
	private final int tweetCount;
	private final int maxNumberOfLikes;
	private final int minNumberOfLikes;
	private final Tweet mostLikedTweet;

	private final int[] likesPercentiles;

	public TweetStatistic(TweetStatisticBuilder tsb)
	{
		super();
		this.tweetAverage = tsb.tweetAverage;
		this.likesAverage = tsb.likesAverage;
		this.likesMedian = tsb.likesMedian;
		this.likesPercentiles = tsb.likesPercentiles;
		this.maxNumberOfLikes = tsb.maxNumberOfLikes;
		this.minNumberOfLikes = tsb.minNumberOfLikes;
		this.tweetCount = tsb.tweetCount;
		this.mostLikedTweet = tsb.mostLikedTweet;
	}
	public Double getTweetAverage()
	{
		return tweetAverage;
	}
	
	public Double getLikesAverage()
	{
		return likesAverage;
	}

	public Long getLikesMedian()
	{
		return likesMedian;
	}

	public int getTweetCount()
	{
		return tweetCount;
	}

	public int[] getLikesPercentiles()
	{
		return likesPercentiles;
	}
	
	public int getMinNumberOfLikes()
	{
		return minNumberOfLikes;
	}
	
	public int getMaxNumberOfLikes()
	{
		return maxNumberOfLikes;
	}
	
	public Tweet getMostLikedTweet()
	{
		return mostLikedTweet;
	}
}
