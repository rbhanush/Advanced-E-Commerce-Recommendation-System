package NaiveBayes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.tartarus.snowball.ext.englishStemmer;

public class analyzer {

    public static ArrayList<String> positive = new ArrayList<String>();
    public static ArrayList<String> negative = new ArrayList<String>();
    public static HashMap<String, Integer> pmap = new HashMap();
    public static HashMap<String, Integer> nmap = new HashMap();

    public static ArrayList<String> analyzer(String comment) throws SQLException {

        /*
         * Create a new classifier instance. The context features are Strings
         * and the context will be classified with a String according to the
         * featureset of the context.
         */
        final Classifier<String, String> NLP = new NLP<String, String>();
        /*
         * The classifier can learn from classifications that are handed over to
         * the learn methods. Imagin a tokenized text as follows. The tokens are
         * the text's features. The category of the text will either be positive
         * or negative.
         */

//		DatabaseConnection db = new DatabaseConnection();
//		db.dbconnection();
//		
//		System.out.println(positive);
//
//		System.out.println(negative);
		// final String[] positiveText =
        // "like Love very good good g8 greate intresting nice".split("\\s");
        NLP.learn("positive", positive);

		// final String[] negativeText =
        // "dont like hate very not bad dump dull boring".split("\\s");
        NLP.learn("negative", negative);

        /*
         * Now that the classifier has "learned" two classifications, it will be
         * able to classify similar sentences. The classify method returns a
         * Classification Object, that contains the given featureset,
         * classification probability and resulting category.
         */
        String[] forstem=comment.split("\\s");
        String maincomment="";
        for(int i=0;i<forstem.length;i++)
        {
        	//System.out.println("get one word at a time"+forstem[i]);
	        englishStemmer stemmer = new englishStemmer();
	        String getword=forstem[i];
	        stemmer.setCurrent(getword);
	        if (stemmer.stem()){
	        	String stemcomment=stemmer.getCurrent();
	        	maincomment=maincomment+" "+stemcomment;
	            //System.out.println("This is stemmer result"+maincomment);
	        }
//	        else
//	        {
//	        	maincomment=maincomment+" "+forstem[i];
//	        }
        }
        final String[] unknownText1 = maincomment.split("\\s");
		// System.out.println( // will output "positive"
        // NLP.classify(Arrays.asList(unknownText1)).getCategory());
        String result = NLP.classify(Arrays.asList(unknownText1)).getCategory();
		// System.out.println(
        // NLP.classify(Arrays.asList(unknownText1)).getCategory());

        /*
         * The BayesClassifier extends the abstract Classifier and provides
         * detailed classification results that can be retrieved by calling the
         * classifyDetailed Method.
         * 
         * The classification with the highest probability is the resulting
         * classification.
         */
        ((NLP<String, String>) NLP).classifyDetailed(Arrays
                .asList(unknownText1));

        /*
         * Please note, that this particular classifier implementation will
         * "forget" learned classifications after a few learning sessions. The
         * number of learning sessions it will record can be set as follows:
         */
        NLP.setMemoryCapacity(500); // remember the last 500 learned
        // classifications
        int rate = 0;
		/////////////////////////////////////////////////////////////////
        if (result.equals("positive")) {
            int loop = 0;
            Set<String> keys = pmap.keySet();
            for (String key : keys) {
                //System.out.println(key);
                if (comment.toLowerCase().contains(key.toLowerCase())) {
                    loop++;
                    rate = rate + pmap.get(key);
                }
            }
            if (rate != 0) {
                rate = rate / loop;
            }
        }

		//////////////////////////////////////////////////////////////negative///////////////////////////////////////
        if (result.equals("negative")) {
            Set<String> keys = nmap.keySet();
            for (String key : keys) {
                //System.out.println(key);
                if (comment.toLowerCase().contains(key.toLowerCase())) {
                    rate = rate + nmap.get(key);
                }
            }
        }

        ArrayList<String> ovwrall = new ArrayList<String>();
        ovwrall.add(comment);
        ovwrall.add(rate + "");
        ovwrall.add(result);
        return ovwrall;
    }
}
