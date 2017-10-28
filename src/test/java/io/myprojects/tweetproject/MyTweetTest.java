package io.myprojects.tweetproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.myprojects.tweetproject.Tweet;
import io.myprojects.tweetproject.TweetController;
import io.myprojects.tweetproject.Tweet.TweetBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyTweetTest
{
	@Autowired
	private TweetController controller;
		
	private Tweet t;
	
	@After
 	public void after() {
		clearMap();
	}

	@Test
	public void getTweetCountTest() {
		TweetBuilder tb = new TweetBuilder(101,201,"");
		controller.addTweet(tb.build());
		assertEquals(1, controller.getTweetCount());
	}
	
	@Test
	public void getTweetTest() {
		TweetBuilder tb = new TweetBuilder(101,201,"");
		controller.addTweet(tb.build());
		assertNotNull(controller.getTweet(101));
	}
	
	@Test
	public void getStatisticByTweetIdTest() {
		TweetBuilder tb = new TweetBuilder(101,201,"");
		tb.setNumOfLikes(10);
		tb.setPublishDate("12/12/2012");
		controller.addTweet(tb.build());
		assertEquals(10, controller.getStats().getMaxNumberOfLikes());
		assertEquals(10, controller.getStats().getMaxNumberOfLikes());
		assertEquals(101, controller.getStats().getMostLikedTweet().getTweetId());
	}
	
	private void clearMap() {
		controller.clear();	
	}
}
