package ReviewsFetcher;

import NaiveBayes.SentenceDetector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements; 

public class fetchreview {

    public static MultiMap multiMap = new MultiValueMap();

//    public static void main(String args[]) throws SQLException {
//        try {
//            CommentExtract("http://www.amazon.in/product-reviews/B01DDP7GZK/ref=cm_cr_arp_d_viewpnt_rgt?ie=UTF8&reviewerType=all_reviews&showViewpoints=1&sortBy=helpful&filterByStar=critical&pageNumber=1");
//        } catch (IOException e) {
//        }
//    }

    public static void CommentExtract(String link,String pid,String site)
            throws IOException, SQLException {
        URL url = new URL(link);
        URLConnection spoof = url.openConnection();

        // Spoof the connection so we look like a web browser
        spoof.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                spoof.getInputStream()));
        String strLine = "";
        String finalHTML = "";
        // Loop through every line in the source
        while ((strLine = in.readLine()) != null) {
            finalHTML = finalHTML + "\n" + strLine;
        }

        String comment = "";
        multiMap.clear();
        Document document = Jsoup.parse(finalHTML);
        Elements divs = document.getElementsByClass("review-text");
        int i = 1;
        for (Element div : divs) {
//            System.out.println(div.ownText().trim());
            comment = div.ownText().trim();
            comment = comment.replaceAll("'", "");
            comment = comment.replaceAll("\"", "");
            
            multiMap.put(i, comment);
            i++;
        }
        preprocessing.Getwords();
        SentenceDetector.SentenceDetector(pid,site);
    }
}
