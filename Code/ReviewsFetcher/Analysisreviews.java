package ReviewsFetcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnection;

/**
 * Servlet implementation class Analysisreviews
 */
@WebServlet("/Analysisreviews")
public class Analysisreviews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analysisreviews() {
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
		PrintWriter out = response.getWriter();
		//System.out.println(request.getParameter("id"));
		/*Thread t = new Thread() {
		    public void run() {*/
		    	try 
				{
					DatabaseConnection db = new DatabaseConnection();
					db.dbconnection();
					String query = "select * from reviewlink";
					ResultSet rs = (ResultSet) db.getResultSet(query);
					while(rs.next())
					{
				    	//System.out.println("hi");
						String id=rs.getString("id");
						String amazonep=rs.getString("amazonep");
						String amazonen=rs.getString("amazonen");
						fetchreview.CommentExtract(amazonep,id,"amazone");
						fetchreview.CommentExtract(amazonen,id,"amazone");
						
						String query1 = "select * from commentanalysis Where id='"+id+"' and sitename='amazone'";
						ResultSet rs1 = (ResultSet) db.getResultSet(query1);
						int ptotal=0;
						int ntotal=0;
						while(rs1.next())
						{
							String subject=rs1.getString("subject");
							if(subject.equals("positive"))
							{
								ptotal=ptotal+1;
							}
							else
							{
								ntotal=ntotal+1;
							}
						}
						int totalsub=ptotal+ntotal;
						int pper=(ptotal*100)/totalsub;
						int nper=(ntotal*100)/totalsub;
//						System.out.println(pper);
//						System.out.println(nper);
						if(!(totalsub==0))
						{
							String recorsdClear = "delete from commentsrate where pid='"+id+"'";
							db.getUpdate(recorsdClear);
						}
							String sql = "Insert into commentsrate value('"+id+"','"+pper+"','"+nper+"','amazone')";
							int i = db.getUpdate(sql);
							if(i==1)
							{
//								out.println("<script type=\"text/javascript\">");
//					        	out.println("alert('Addedd successfully')");
//					        	out.println("location=\"login.jsp\"");
//					        	out.println("</script>");
							}
					}
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
//		    };
//		    t.start();
		
	}

}
