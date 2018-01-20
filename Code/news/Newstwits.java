package news;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseConnection;

/**
 * Servlet implementation class Productstwits
 */
@WebServlet("/Newstwits")
public class Newstwits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Newstwits() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		
		String query = "select * from news";
		ResultSet rs = (ResultSet) db.getResultSet(query);
		
	try {
			while(rs.next()){
				String pid=rs.getString("id");
				String twits=rs.getString("twits");
				ArrayList<String> tweetList = new ArrayList<String>();
				TweetManager11 td=new TweetManager11();
				tweetList=td.getTweets(twits);
				System.out.println(tweetList);
				
				if(!(tweetList.equals("")))
				{
					String recorsdClear = "delete from newstwitsrate where pid='"+twits+"'";
					db.getUpdate(recorsdClear);
				}
				String query1 = "select * from newstwitsanalysis Where pid='"+twits+"'";
				ResultSet rs1 = (ResultSet) db.getResultSet(query1);
				int ptotal=0;
				int ntotal=0;
				while(rs1.next())
				{
					String subject=rs1.getString("result");
					if(subject.equals("positive"))
					{
						ptotal=ptotal+1;
					}
					else if(subject.equals("negative"))
					{
						ntotal=ntotal+1;
					}
				}
				int totalsub=ptotal+ntotal;
				int pper=0;
				int nper=0;
				if(totalsub > 0){
					pper=(ptotal*100)/totalsub;
					nper=(ntotal*100)/totalsub;
				}
					System.out.println(pper);
					System.out.println(nper);
					String sql = "Insert into newstwitsrate value('"+pid+"','"+pper+"','"+nper+"')";
					int i = db.getUpdate(sql);
					if(i==1)
					{
						out.println("<script type=\"text/javascript\">");
			        	out.println("alert('Addedd successfully')");
			        	out.println("location=\"music/Viewmusic.jsp\"");
			        	out.println("</script>");
					}
	        	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
