package news;

import java.util.ArrayList;
import java.util.List;

import twitter.Main;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetManager11 {
	
//	public static void main(String args[]) {
//		ArrayList<String> tweetList = new ArrayList<String>();
//		TweetManager td=new TweetManager();
//		tweetList=td.getTweets("Narendar Modi");
//		System.out.println(tweetList);
//	}

    public static ArrayList<String> getTweets(String topic) {
    	Main11.main(topic);
    	Twitter twitter =Main11.getTwitter();// new TwitterFactory().getInstance();
        ArrayList<String> tweetList = new ArrayList<String>();
        try {
//            Query query = new Query(topic);
//            System.out.println("query "+query);
            QueryResult result;
            
            Query q = new Query(topic);			// Search for tweets that contains this term
			q.setCount(Main11.TWEETS_PER_QUERY);				// How many tweets, max, to retrieve
//			q.setResultType("recent");						// Get all tweets
			q.setLang("en");	
			
            do {
                result = twitter.search(q);
//                System.out.println("result "+result);
                List<Status> tweets = result.getTweets();
//                System.out.println("tweets "+tweets);
                for (Status tweet : tweets) {
                    tweetList.add(tweet.getText());
                }
            } while ((q = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return tweetList;
    }
}