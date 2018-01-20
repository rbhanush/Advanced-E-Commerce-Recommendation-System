package User;

import info.debatty.java.stringsimilarity.Jaccard;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tartarus.snowball.ext.englishStemmer;

import NaiveBayes.StopwordRemoval;

import database.DatabaseConnection;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Map<String, Double> hashmap = new HashMap<>();
		PrintWriter out = response.getWriter();
		 StopwordRemoval stop=new StopwordRemoval();
         stop.LoadStopWord();
		ArrayList<String> getresultid=new ArrayList<String>();
		String keyword=request.getParameter("Search");
/*************************************************************************/		
/**************************Stemming Code************************************/
/*************************************************************************/		
			String[] forstem=keyword.split("\\s");
	        String maincomment="";
	        for(int i=0;i<forstem.length;i++)
	        {
		        englishStemmer stemmer = new englishStemmer();
		        String getword=forstem[i];
		        stemmer.setCurrent(getword);
		        if (stemmer.stem()){
		        	String stemcomment=stemmer.getCurrent();
		        	maincomment=maincomment+" "+stemcomment;
		            //System.out.println("This is stemmer result"+maincomment);
		        }
	        }
        	keyword=maincomment;
        	
        	// Code for Stop word removal//
        	String keyword1=stop.StopwordRemoval(keyword);
            System.out.println("Stop word remove from comment:-"+ keyword1);
/*************************************************************************/  
/*************************************************************************/        	
		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		
		String query = "select * from products";
		ResultSet rs = (ResultSet) db.getResultSet(query);
		try {
				while(rs.next())
				{
					String pid=rs.getString("id");
					String description=rs.getString("description");
		/***************************************************************************/			
		/**************************Stemming Code************************************/
		/***************************************************************************/
					String[] forstem1=description.split("\\s");
			        String maincomment1="";
			        for(int i1=0;i1<forstem1.length;i1++)
			        {
			        	//System.out.println("get one word at a time"+forstem[i]);
				        englishStemmer stemmer1 = new englishStemmer();
				        String getword1=forstem1[i1];
				        stemmer1.setCurrent(getword1);
				        if (stemmer1.stem()){
				        	String stemcomment1=stemmer1.getCurrent();
				        	maincomment1=maincomment1+" "+stemcomment1;
				            //System.out.println("This is stemmer result"+maincomment);
				        }
			        }
			        description=maincomment1;

			       // Code for Stop word removal//
			        String description1=stop.StopwordRemoval(description);
		            System.out.println("Stop word remove from description:-"+ description1);
		/*************************************************************************/
		/*************************************************************************/			        
					String[] onedesc=description1.split(",");
					double result=0.0;
					double maxresult =0.0;
					int j=0;
					int i=0;
					ArrayList<Double> simresultadd = new ArrayList<Double>();
					
					for(i=0;i<onedesc.length;i++)
					{
						//System.out.println(onedesc[i]);
		/*************************************************************************/
		/*********Description,Functional & Characterstics Similarity**************/
		/*************************************************************************/
						Jaccard sim=new Jaccard();
						result=sim.similarity(keyword1,onedesc[i]);
						simresultadd.add(result);
//						if(result >= 0.49)
//						{
//							j++;
//						}
		/*************************************************************************/
		/*************************************************************************/
					}
					maxresult = Collections.max(simresultadd);
					System.out.println("this per one maxresult"+maxresult);
					hashmap.put(pid, maxresult);
				}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		double maxValueInMap = (Collections.max(hashmap.values()));  // This will return max value in the Hashmap
        for (Map.Entry<String, Double> entry : hashmap.entrySet()) {// Itrate through hashmap
            if (entry.getValue() <= maxValueInMap) {
               	String productid = entry.getKey();
               	getresultid.add(productid);
            }
        }
        
        String uid=(String)session.getAttribute("uid");
        String location=(String)session.getAttribute("location");
        String queryText = "insert into search(uid, searchq, result, location) values('"+uid+"','"+keyword+"','"+getresultid+"','"+ location+"')";	
        int i = db.getUpdate(queryText);
        
        System.out.println("this per one maxreid"+getresultid);
		session.setAttribute("searchresult", getresultid);
		response.sendRedirect("user/search.jsp");
	}

}
