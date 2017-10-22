package io.myporjects.tweetproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyTweetService implements TweetService {

    private Map<Integer,Tweet> tweetMap;
    private List<Tweet> tweetArray;
    private TweetStatistic tweetStat;
    
	@Value("${base.module.likesPercentileArray}")
	private String[] likesPercentileArray;
    

	public int getTweetCount() {
        if (tweetMap != null){
            return tweetMap.size();
        }
        return 0;
    }

    public Tweet getTweet(int tweetId) {
        if(tweetMap.containsKey(tweetId)) {
            return tweetMap.get(tweetId);
        }
        return null;
    }

    public void addTweet(Tweet tweet) {
    	if(tweetMap.containsKey(tweet.getTweetId())){
    		return;
    	}
    	
        if (tweetMap == null){
            tweetMap = new HashMap<Integer,Tweet>();
        }
        if (tweetArray == null){
        	tweetArray = new ArrayList<Tweet>();
        }
    	tweetMap.put(tweet.getTweetId(),tweet);
    	tweetArray.add(tweet);
    }

    public void addTweets(Collection<Tweet> tweets) {
    	if (tweetMap == null){
    		 tweetMap = new HashMap<Integer,Tweet>();
    	}
        if (tweetArray == null)
        {
        	tweetArray = new ArrayList<Tweet>();
        }
    	for(Tweet tweet : tweets){
        	if(tweetMap.containsKey(tweet.getTweetId())){
        		continue;
        	}
			 tweetMap.put(tweet.getTweetId(),tweet);
			 tweetArray.add(tweet);
		}
    }

	public TweetStatistic getStats() {
		if ((tweetArray == null ||tweetArray.isEmpty()) 
				&& (tweetMap == null || tweetMap.isEmpty())){
			return null;
		}
		Double tweetAvg = getAvgOfTweets();
		Double likesAvg = getAvgOfLikes();
		Long likesMedian = getLikesMedian();
		int minNumberOfLikes = getMinNumberOfLikes();
		int maxNumberOfLikes = getMaxNumberOfLikes();
		Tweet mostLikedTweet = getMostLikedTweet();
		int[] likesPercentiles = getLikesPercentiles();
		tweetStat = new TweetStatisticBuilder()
				.setTweetAverage(tweetAvg)
				.setLikesAverage(likesAvg)
				.setLikesMedian(likesMedian)
				.setLikesPercentiles(likesPercentiles)
				.setTweetCount(tweetMap.size())
				.setMaxNumberOfLikes(maxNumberOfLikes)
				.setMinNumberOfLikes(minNumberOfLikes)
				.setMostLikedTweet(mostLikedTweet)
				.build();
		return tweetStat;
	}
	
	private Tweet getMostLikedTweet() {
		int maxLikes=0;
		int tweetId=0;
		for(Tweet tweet : tweetArray){
			if(tweet.getNumOfLikes() > maxLikes){
				maxLikes = tweet.getNumOfLikes();
				tweetId = tweet.getTweetId();
			}
		}
		return getTweet(tweetId);
	}

	private int getMaxNumberOfLikes() {
		int maxLikes=0;
		boolean wasUpdated=false;
		for(Tweet tweet : tweetArray){
			if(wasUpdated == false || tweet.getNumOfLikes() > maxLikes){
				maxLikes = tweet.getNumOfLikes();
				wasUpdated = true;
			} 
		}
		return maxLikes;
	}

	private int getMinNumberOfLikes() {
		int minLikes=0;
		boolean wasUpdated=false;
		for(Tweet tweet : tweetArray){
			if(wasUpdated == false || tweet.getNumOfLikes() < minLikes){
				minLikes = tweet.getNumOfLikes();
				wasUpdated = true;
			}
		}
		return minLikes;
	}

	private Double getAvgOfTweets() {
		double sum = tweetMap.size();
		if (sum>0)
		{
			Double avg = sum/tweetMap.size();
			return avg;
		}
		else{
			return sum;
		}
	}

	private Double getAvgOfLikes() {
		double sum = getSumOfLikes();
		if (sum>0)
		{
			Double avg = sum/tweetMap.size();
			return avg;
		}
		else{
			return sum;
		}
	}
	
	private double getSumOfLikes() {
		double sum=0;
		for(Tweet tweet : tweetArray){
			sum+=tweet.getNumOfLikes();
		}
		return sum;
	}

	private Long getLikesMedian() {
		Long medianValue = null;
		int [] likesArray = getLikesArrayFromTweetList(tweetArray);
		Arrays.sort(likesArray);
		int median = likesArray.length /2;
		if (likesArray.length % 2 == 1) {
			medianValue = (long)likesArray[median];
			return medianValue;
		} else {
			medianValue = (long)(likesArray[median - 1] + likesArray[median]) / 2;
			return medianValue;
		}
	}

	private int[] getLikesArrayFromTweetList(List<Tweet> tweetList){
		int [] likesArr = new int[tweetList.size()]; 
		for (int i=0;i<tweetList.size();i++){
			likesArr[i] = tweetList.get(i).getNumOfLikes();
		}
		return likesArr;
	}

	private int[] getLikesPercentiles() {		
		int [] likesArray = getLikesArrayFromTweetList(tweetArray);
		Double[] likesPercentiles = getDoubleArray(likesPercentileArray);
		int[] likesPercentilesResult = percentiles(likesArray , likesPercentiles);
		return likesPercentilesResult;	
	}

	private Double[] getDoubleArray(String[] likesPercentileArray) {
		Double[] likesPercentilesDoubleArr = new Double[likesPercentileArray.length];
		for (int i = 0; i < likesPercentileArray.length; i++) {
			likesPercentilesDoubleArr[i] = (Double.parseDouble(likesPercentileArray[i]))/100;
		}
		return likesPercentilesDoubleArr;
	}

	private static int[] percentiles(int[] latencies, Double... percentiles) {
		Arrays.sort(latencies, 0, latencies.length);
		int[] values = new int[percentiles.length];
		for (int i = 0; i < percentiles.length; i++) {
			int index = (int) (percentiles[i] * latencies.length);
			values[i] = latencies[index];
		}
		return values;
	}

	public TweetStatistic getStats(int tweetId)
	{
		for(Tweet tweet : tweetArray)
		{
			if (tweetId == tweet.getTweetId())
				return getStats(tweetId);
		}
		return null;
	}
}
