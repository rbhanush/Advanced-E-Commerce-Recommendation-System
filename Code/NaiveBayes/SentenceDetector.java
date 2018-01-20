package NaiveBayes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import database.DatabaseConnection;
import ReviewsFetcher.fetchreview;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SentenceDetector {
	
	public ArrayList<String> SentenceSplitter(String comment) {
		ArrayList<String> Sentences = new ArrayList<String>();
		Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
	    Matcher reMatcher = re.matcher(comment);
	    while (reMatcher.find()) {
	    	String sentnce = reMatcher.group().trim();
	    	if(!sentnce.equals("")){
	    		Sentences.add(sentnce);
	    	}
	    }
		return Sentences;
	}
	
	public static void SentenceDetector(String pid,String sitename) throws SQLException, IOException {

		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();	
		analyzer a = new analyzer();
                Map<Integer, String> sortedMapByKeys = new TreeMap<>();
                    sortedMapByKeys.putAll(fetchreview.multiMap);
                    Iterator iter = fetchreview.multiMap.entrySet().iterator();
                    StopwordRemoval stop=new StopwordRemoval();
                    stop.LoadStopWord();
                    while (iter.hasNext()) {
                        Map.Entry pairs = (Map.Entry) iter.next();
                        String comment = pairs.getValue().toString().replaceAll("\\[|\\]", "").toLowerCase().trim();
                        String comment1=stop.StopwordRemoval(comment);
                        System.out.println("Stop word remove from comment:-"+ comment1);
			ArrayList<String> result = a.analyzer(comment1);
			//System.out.println("Comment: "+comment);
			if(!(result.get(2).equals("")))
			{
				String recorsdClear = "delete from commentanalysis where comment='"+comment+"'";
				db.getUpdate(recorsdClear);
				String query = "";
				if (result.contains("negative")) {
	                            //System.out.println(result);
					query = "insert into  commentanalysis values(" + pid + ",'"+ result.get(2) +"','"+ result.get(0) + "','0','"+ result.get(1) + "','"+sitename+"')";
				} else {
	                           // System.out.println(result);
					String getre=result.get(1);
					if(getre.equals("0"))
					{
						query = "insert into  commentanalysis values(" + pid + ",'normal','"+ result.get(0) + "','"+ result.get(1) + "','0','"+sitename+"')";
					}
					else
					{
						query = "insert into  commentanalysis values(" + pid + ",'"+ result.get(2) +"','"+ result.get(0) + "','"+ result.get(1) + "','0','"+sitename+"')";
					}
				}
				db.getUpdate(query);
			}
		}
		
//		String query1 = "SELECT DISTINCT subject FROM commentanalysis where id = '"+id+"' ";
//		System.out.println(query1);
//		
//		ArrayList<String> sub = new ArrayList<String>();
//		ResultSet rs = db.getResultSet(query1);
//		while(rs.next())
//			{
//			sub.add(rs.getString(1));
//			}
//		
//        for (int i = 0; i < sub.size(); i++) { 
//        	System.out.println(sub.size());
//            query = "select * from commentanalysis where id = '" + id + "' and subject='" + sub.get(i) + "'";
//            rs = db.getResultSet(query);
//            
//            double ploop = 0;
//            double nloop = 0;
//            double pv = 0;
//            double nv = 0;
//            while (rs.next()) {
//                if (rs.getInt("prate") > 0) {
//                    pv = pv + rs.getInt("prate");
//                    ploop++;
//                }
//
//                if (rs.getInt("nrate") > 0) {
//                    nv = nv + rs.getInt("nrate");
//                    nloop++;
//                }
//            }
//
//            double pr = 0;
//            double nr = 0;
//            if (ploop != 0) {
//                pr = pv / ploop;
//                System.out.println("Final positive rating: " + pr);
//            } else {
//                System.out.println("No Positive Feedback");
//            }
//            if (nloop != 0) {
//                nr = nv / nloop;
//                System.out.println("Final negative rating: " + nr);
//            } else {
//                System.out.println("No NEgative Feedback");
//            }
//            
//            String rate = pr+","+nr;      
//            query = " SELECT id FROM collegerating WHERE id LIKE '%"+id+"%'";
//            rs = db.getResultSet(query);
//            if(rs.next()){
//            	String subject = sub.get(i);
//            	query=  "Update collegerating set "+subject+"='"+rate+"' where id='"+id+"'";
//                db.getUpdate(query);
//            }
//            else
//            {
//            query=  "insert into collegerating (id," + sub.get(i) + ") values('"+id+"','"+rate+"')";
//            db.getUpdate(query);
//            }
//        }
//        
//        int totalsub = sub.size();
//        double totalpvrate = 0.0;
//        query = "select * from collegerating where id = '" + id + "'";
//        rs = db.getResultSet(query);
//        
//        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//        int columnCount = rsmd.getColumnCount();
//
//        // The column count starts from 1
//        if(rs.next()){
//        for (int i = 2; i <= columnCount; i++ ) {
//          String columnname = rsmd.getColumnName(i);
//          String[] subject = rs.getString(columnname).split(",");
//          totalpvrate = totalpvrate + Double.valueOf(subject[0]);
//        }
//        }
//        double avgpv = totalpvrate/totalsub;
//        System.out.println("avgpv "+avgpv);
//        String rate = String.format("%.01f", avgpv);
//        query=  "Update college set rate='"+rate+"' where id='"+id+"'";
//        db.getUpdate(query);
	}
}
