package io.myporjects.tweetproject;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    @Autowired
    TweetService tweetService;

    @RequestMapping("/count")
    public int getTweetCount() { return tweetService.getTweetCount(); }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("/getTweet/{tweetId}")
    public Tweet getTweet(@PathVariable int tweetId) {
        return tweetService.getTweet(tweetId);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST, value = "/addTweet")
    public void addTweet(@RequestBody Tweet tweet) {
        tweetService.addTweet(tweet);
    }
    
    @Consumes(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST, value = "/addTweets")
    public void addTweets(@RequestBody TweetWrapper tweets) {
            tweetService.addTweets(tweets.getTweets());
    }
    
	@RequestMapping("/stat")
	public TweetStatistic getStats()
	{
		return tweetService.getStats();
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping("/stat/{tweetId}")
	public TweetStatistic getStatsByTweetId(@PathVariable int tweetId)
	{
		return tweetService.getStats(tweetId);
	}
}
