package User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseConnection;

/**
 * Servlet implementation class inserttransaction
 */
@WebServlet("/inserttransaction")
public class inserttransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public inserttransaction() {
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
		HttpSession session = request.getSession();
		String uid =(String) session.getAttribute("uid");
        String cname = request.getParameter("cname");
        String cno = request.getParameter("cno");
        String cexp = request.getParameter("cexp");
        String csecu = request.getParameter("csecu");
        String zip = request.getParameter("zip");
        String amt = request.getParameter("amt");
        String totalpid="";
        int totalqty=0;
        DatabaseConnection db = new DatabaseConnection();
   		db.dbconnection();
   		
   		
	   		String query = "select * from cart where uid = '" +uid+ "'";
			ResultSet rs = (ResultSet) db.getResultSet(query);
			try 
			{
				while(rs.next())
				{
					String pid=rs.getString("pid");
					if(totalpid.equals(""))
					{
						totalpid=pid;
					}
					else
					{
						totalpid=totalpid+","+pid;
					}
					
					String qty=rs.getString("qty");
					int qty1=Integer.parseInt(qty);
					totalqty=totalqty+qty1;
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

        String queryText = "INSERT INTO transaction(uid,cname,cno,cexp,csecu,zip,amt,totalpid,totalqty) VALUES('"+uid+"','"+cname+"','"+ cno+"','"+ cexp+"', '"+ csecu+"','"+ zip+"','"+ amt+"','"+totalpid+"','"+totalqty+"')";
        int i = db.getUpdate(queryText);
        if(i==0)
        {
        	out.println("<script type=\"text/javascript\">");
        	out.println("alert('Please provide all values')");
        	out.println("location=\"user/transaction.jsp");
        	out.println("</script>");
        }
        else
        {
        	String queryText1 = "DELETE FROM cart WHERE uid = '"+uid+"'";
            int i1 = db.getUpdate(queryText1);
        	out.println("<script type=\"text/javascript\">");
        	out.println("alert('Transaction Done Successfully')");
        	out.println("location=\"user/products.jsp\"");
        	out.println("</script>");
        }
	}

}
