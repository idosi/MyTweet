package io.myprojects.tweetproject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyTweetService implements TweetService {

    private Map<Integer,Tweet> tweetMap = new HashMap<Integer, Tweet>();
    private TweetStatistic tweetStat;
    private final Comparator<Tweet> comp = (t1, t2) -> Integer.compare( t1.getNumOfLikes(), t2.getNumOfLikes());
    
	@Value("${base.module.likesPercentileArray}")
	private String[] likesPercentileArray;
    

	public int getTweetCount() {
        return tweetMap.size();
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
    	tweetMap.put(tweet.getTweetId(), tweet);
    }

    public void addTweets(Collection<Tweet> tweets) {
    	for(Tweet tweet : tweets){
    		addTweet(tweet);
		}
    }

	public TweetStatistic getStats() {
		if (tweetMap.isEmpty()){
			return null;
		}
		
		long sumOfLikes = getSumOfLikes(tweetMap.values());
		double likesAvg = getAvgOfLikes(tweetMap.values());
		long likesMedian = getLikesMedian(tweetMap.values());
		long minNumberOfLikes = getMinNumberOfLikes(tweetMap.values());
		long maxNumberOfLikes = getMaxNumberOfLikes(tweetMap.values());
		Tweet mostLikedTweet = getMostLikedTweet(tweetMap.values());
		int[] likesPercentiles = getLikesPercentiles(tweetMap.values());
		tweetStat = new TweetStatistic.TweetStatisticBuilder()
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
	
	private Tweet getMostLikedTweet(Collection<Tweet> localTweetArray) {		
		return getTweet(localTweetArray.stream().max(comp).get().getTweetId());
	}

	private int getMaxNumberOfLikes(Collection<Tweet> localTweetArray) {
		return localTweetArray.stream().max(comp).get().getNumOfLikes();
	}

	private int getMinNumberOfLikes(Collection<Tweet> localTweetArray) {
		return localTweetArray.stream().min(comp).get().getNumOfLikes();
	}

	private double getAvgOfLikes(Collection<Tweet> localTweetArray) {
		return localTweetArray.stream().mapToDouble(l -> l.getNumOfLikes()).average().getAsDouble();
	}
	
	private long getSumOfLikes(Collection<Tweet> localTweetArray) {
		return localTweetArray.stream().mapToLong(l -> l.getNumOfLikes()).sum();
	}

	private long getLikesMedian(Collection<Tweet> localTweetArray) {
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

	private Integer[] getLikesArrayFromTweetList(Collection<Tweet> tweetList) {		
		return tweetList.stream().map(tweet-> tweet.getNumOfLikes()).toArray(Integer[]::new);
	}

	private int[] getLikesPercentiles(Collection<Tweet> localTweetArray) {		
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
		Map<Integer, Tweet> userTweetsMap = tweetMap.values().stream().filter(x -> userId == x.getUserId())
				.collect(Collectors.toMap(Tweet::getTweetId, Function.identity()));
		return getStat(userTweetsMap);
	}
	
	private TweetStatistic getStat(Map<Integer, Tweet> tempTweetMap) {
		long sumOfLikes = getSumOfLikes(tempTweetMap.values());
		double likesAvg = getAvgOfLikes(tempTweetMap.values());
		long likesMedian = getLikesMedian(tempTweetMap.values());
		long minNumberOfLikes = getMinNumberOfLikes(tempTweetMap.values());
		long maxNumberOfLikes = getMaxNumberOfLikes(tempTweetMap.values());
		Tweet mostLikedTweet = getMostLikedTweet(tempTweetMap.values());
		int[] likesPercentiles = getLikesPercentiles(tempTweetMap.values());
		tweetStat = new TweetStatistic.TweetStatisticBuilder()
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
	}
}
