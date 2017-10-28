package io.myprojects.tweetproject;

public class Tweet {

    private final int tweetId;
    private final int userId;
    private final String publishDate;
    private final String tweetContent;
    private final int refTweetId;
    private final int numOfLikes;
    
    private Tweet() {
    	 this.tweetId = 0;
         this.userId = 0;
         this.publishDate = "";
         this.tweetContent  = "";
         this.refTweetId = 0;
         this.numOfLikes = 0;
    }
    
    public Tweet(TweetBuilder tb) {
        super();
        this.tweetId = tb.tweetId;
        this.userId = tb.userId;
        this.publishDate = tb.publishDate;
        this.tweetContent  = tb.tweetContent;
        this.refTweetId = tb.refTweetId;
        this.numOfLikes = tb.numOfLikes;
    }

    static public class TweetBuilder {
        protected int tweetId;
        protected int userId;
        protected String publishDate;
        protected String tweetContent;
        protected int refTweetId;
        protected int numOfLikes;

        public TweetBuilder(int tweetId ,int userId ,String tweetContent) {
            this.tweetId = tweetId;
            this.userId = userId;
            this.tweetContent = tweetContent;
        }

        public TweetBuilder setTweetContent(String tweetContent) {
            this.tweetContent = tweetContent;
            return this;
        }

        public TweetBuilder setNumOfLikes(int numberNumOfLikes) {
            this.numOfLikes = numberNumOfLikes;
            return this;
        }

        public TweetBuilder setRefTeewtId(int refTweetId) {
            this.refTweetId = refTweetId;
            return this;
        }

        public TweetBuilder setPublishDate(String publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public TweetBuilder() {
            super();
        }

        public Tweet build() { return new Tweet(this); }
    }

    public int getTweetId() {
        return tweetId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public int getRefTweetId() {
        return refTweetId;
    }

    public String getTweetContent() {
        return tweetContent;
    }
}
