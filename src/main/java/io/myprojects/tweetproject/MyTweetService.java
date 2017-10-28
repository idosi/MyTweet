package io.myprojects.tweetproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyTweetService implements TweetService {

    private Map<Integer,Tweet> tweetMap;
    private List<Tweet> tweetArray;
    private TweetStatistic tweetStat;
    private final Comparator<Tweet> comp = (t1, t2) -> Integer.compare( t1.getNumOfLikes(), t2.getNumOfLikes());
    
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
        if (tweetMap == null){
            tweetMap = new HashMap<Integer,Tweet>();
        }
    	if(tweetMap.containsKey(tweet.getTweetId())){
    		return;
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
		double sumOfLikes = getSumOfLikes(tweetArray);
		double likesAvg = getAvgOfLikes(tweetMap, tweetArray);
		long likesMedian = getLikesMedian(tweetArray);
		int minNumberOfLikes = getMinNumberOfLikes(tweetArray);
		int maxNumberOfLikes = getMaxNumberOfLikes(tweetArray);
		Tweet mostLikedTweet = getMostLikedTweet(tweetArray);
		int[] likesPercentiles = getLikesPercentiles(tweetArray);
		tweetStat = new TweetStatisticBuilder()
				.setSumOfLikes(sumOfLikes)
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
	
	private Tweet getMostLikedTweet(List<Tweet> localTweetArray) {		
		return getTweet(localTweetArray.stream().max(comp).get().getTweetId());
	}

	private int getMaxNumberOfLikes(List<Tweet> localTweetArray) {
		return localTweetArray.stream().max(comp).get().getNumOfLikes();
	}

	private int getMinNumberOfLikes(List<Tweet> localTweetArray) {
		return localTweetArray.stream().min(comp).get().getNumOfLikes();
	}

	private double getAvgOfLikes(Map<Integer, Tweet> localTweetMap, List<Tweet> localTweetArray) {
		return localTweetArray.stream().mapToDouble(l -> l.getNumOfLikes()).average().getAsDouble();
	}
	
	private long getSumOfLikes(List<Tweet> localTweetArray) {
		return localTweetArray.stream().mapToLong(l -> l.getNumOfLikes()).sum();
	}

	private long getLikesMedian(List<Tweet> localTweetArray) {
		long medianValue;
		Integer [] likesArray = getLikesArrayFromTweetList(localTweetArray);
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

	private Integer[] getLikesArrayFromTweetList(List<Tweet> tweetList) {		
		return tweetList.stream().map(tweet-> tweet.getNumOfLikes()).toArray(Integer[]::new);
	}

	private int[] getLikesPercentiles(List<Tweet> localTweetArray) {		
		Integer [] likesArray = getLikesArrayFromTweetList(localTweetArray);
		Double[] likesPercentiles = getDoubleArray(likesPercentileArray);
		int[] likesPercentilesResult = percentiles(likesArray, likesPercentiles);
		return likesPercentilesResult;	
	}

	private Double[] getDoubleArray(String[] likesPercentileArray) {
		Double[] likesPercentilesDoubleArr = Arrays.stream(likesPercentileArray).map(Double::valueOf).toArray(Double[]::new);
		return Arrays.stream(likesPercentilesDoubleArr).map(p->p/100).toArray(Double[]::new);		
	}

	private static int[] percentiles(Integer[] latencies, Double... percentiles) {
		Arrays.sort(latencies, 0, latencies.length);
		int[] values = new int[percentiles.length];
		for (int i = 0; i < percentiles.length; i++) {
			int index = (int) (percentiles[i] * latencies.length);
			values[i] = latencies[index];
		}
		return values;
	}

	public TweetStatistic getStats(int userId) {
		 Map<Integer,Tweet> tempTweetMap = new HashMap<Integer,Tweet>();
		 List<Tweet> tempTweetArray = new ArrayList<Tweet>();
		 
		for(Tweet tweet : tweetArray) {
			if (userId == tweet.getUserId()) {
				tempTweetMap.put(tweet.getTweetId(), tweet);
				tempTweetArray.add(tweet);
			}
		}

		if (tempTweetArray.isEmpty())
		{
			return null;
		}
		return getStat(tempTweetMap, tempTweetArray);
	}
	
	private TweetStatistic getStat(Map<Integer, Tweet> tempTweetMap, List<Tweet> tempTweetArray) {
		double sumOfLikes = getSumOfLikes(tempTweetArray);
		double likesAvg = getAvgOfLikes(tempTweetMap, tempTweetArray);
		long likesMedian = getLikesMedian(tempTweetArray);
		int minNumberOfLikes = getMinNumberOfLikes(tempTweetArray);
		int maxNumberOfLikes = getMaxNumberOfLikes(tempTweetArray);
		Tweet mostLikedTweet = getMostLikedTweet(tempTweetArray);
		int[] likesPercentiles = getLikesPercentiles(tempTweetArray);
		tweetStat = new TweetStatisticBuilder()
				.setSumOfLikes(sumOfLikes)
				.setLikesAverage(likesAvg)
				.setLikesMedian(likesMedian)
				.setLikesPercentiles(likesPercentiles)
				.setTweetCount(tempTweetMap.size())
				.setMaxNumberOfLikes(maxNumberOfLikes)
				.setMinNumberOfLikes(minNumberOfLikes)
				.setMostLikedTweet(mostLikedTweet)
				.build();
		return tweetStat;		
	}

	public void clear() {
		tweetMap.clear();
		tweetArray.clear();
	}
}
