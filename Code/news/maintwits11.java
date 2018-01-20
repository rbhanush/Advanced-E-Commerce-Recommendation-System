package news;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class maintwits11 {

	/**
	 * @param args
	 * @throws TwitterException 
	 */
	public static void main(String[] args) throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		   Twitter twitter = TwitterFactory.getSingleton();
	          twitter.setOAuthConsumer("jGZGAOCo8KP1Kzi2NiUQfNYrl", "D70VroiuYiQ8DpPmebAt6bB4SVlvJsd2Wr5EMCsSPUNsMhgqfT");

	        Query query = new Query("Narendra Modi");
	        QueryResult result;
	        do {
	            result = twitter.search(query);
	            List<Status> tweets = result.getTweets();
	            for (Status tweet : tweets) {
	                System.out.println("@" + tweet.getUser().getScreenName() + 
	                     " - " + tweet.getText());
	                }
	            } while ((query = result.nextQuery()) != null); 

	}

}
