package io.myprojects.tweetproject;

public class TweetStatistic {
	private final long sumOfLikes;
	private final double likesAverage;
	private final long likesMedian;
	private final int tweetCount;
	private final long maxNumberOfLikes;
	private final long minNumberOfLikes;
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
	
	public long getMinNumberOfLikes() { return minNumberOfLikes; }
	
	public long getMaxNumberOfLikes() { return maxNumberOfLikes; }
	
	public Tweet getMostLikedTweet() {return mostLikedTweet;}
	
	public static class TweetStatisticBuilder {
		protected long sumOfLikes;
		protected double likesAverage;
		protected long likesMedian;
		protected int tweetCount;
		protected long maxNumberOfLikes;
		protected long minNumberOfLikes;
		protected Tweet mostLikedTweet;

		protected int[] likesPercentiles;
		
		public TweetStatisticBuilder() { super(); }
		
		public TweetStatisticBuilder setSumOfLikes(long sumOfLikes) {
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
		
		public TweetStatisticBuilder setMaxNumberOfLikes(long maxNumberOfLikes) {
			this.maxNumberOfLikes = maxNumberOfLikes;
			return this;
		}

		public TweetStatisticBuilder setMinNumberOfLikes(long minNumberOfLikes) {
			this.minNumberOfLikes = minNumberOfLikes;
			return this;
		}
		
		public TweetStatisticBuilder setMostLikedTweet(Tweet mostLikedTweet) {
			this.mostLikedTweet = mostLikedTweet;
			return this;
		}
		
	    public TweetStatistic build() {	return new TweetStatistic(this); }
	}
}
