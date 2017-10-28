package io.myprojects.tweetproject;

public class TweetStatistic {
	private final double sumOfLikes;
	private final double likesAverage;
	private final long likesMedian;
	private final int tweetCount;
	private final int maxNumberOfLikes;
	private final int minNumberOfLikes;
	private final Tweet mostLikedTweet;

	private final int[] likesPercentiles;

	public TweetStatistic(TweetStatisticBuilder tsb) {
		super();
		this.sumOfLikes = tsb.sumOfLikes;
		this.likesAverage = tsb.likesAverage;
		this.likesMedian = tsb.likesMedian;
		this.likesPercentiles = tsb.likesPercentiles;
		this.maxNumberOfLikes = tsb.maxNumberOfLikes;
		this.minNumberOfLikes = tsb.minNumberOfLikes;
		this.tweetCount = tsb.tweetCount;
		this.mostLikedTweet = tsb.mostLikedTweet;
	}
	
	public double getSumOfLikes() { return sumOfLikes; }
	
	public double getLikesAverage() { return likesAverage; }

	public long getLikesMedian() { return likesMedian; }

	public int getTweetCount() { return tweetCount; }

	public int[] getLikesPercentiles() { return likesPercentiles; }
	
	public int getMinNumberOfLikes() { return minNumberOfLikes; }
	
	public int getMaxNumberOfLikes() { return maxNumberOfLikes; }
	
	public Tweet getMostLikedTweet() {return mostLikedTweet;}
}
