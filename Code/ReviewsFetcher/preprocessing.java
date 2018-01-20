package ReviewsFetcher;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;
import NaiveBayes.SentenceDetector;
import NaiveBayes.analyzer;

public class preprocessing {

    public static void Getwords() throws SQLException, IOException {
        DatabaseConnection db = new DatabaseConnection();
        db.dbconnection();
        SentenceDetector s = new SentenceDetector();
        analyzer a = new analyzer();
        String query = "Select * from wordscollection";
        ResultSet rs = (ResultSet) db.getResultSet(query);
        while (rs.next()) {
            if (rs.getString("positivecollection").trim().length() > 2) {
                a.positive.add(rs.getString("positivecollection").trim());
                a.pmap.put(rs.getString("positivecollection"), rs.getInt("prate"));
            }
            if (rs.getString("negativecollection").trim().length() > 2) {
                a.negative.add(rs.getString("negativecollection").trim());
                a.nmap.put(rs.getString("negativecollection"), rs.getInt("nrate"));
            }
        }
    }
}
