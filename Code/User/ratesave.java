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
 * Servlet implementation class Login
 */
@WebServlet("/ratesave")
public class ratesave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ratesave() {
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
		String rate = request.getParameter("str");
		String pid = request.getParameter("pid");
		String uid =(String) session.getAttribute("uid");

		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		String query = "Select * From rate Where username='"+uid+"' and pid='"+pid+"'";
		ResultSet rs =db.getResultSet(query);
		try {
			if(!rs.next())
			{
				String query1 = "Insert Into rate values('"+pid+"','"+rate+"','"+uid+"')";
				int i =db.getUpdate(query1);

				if(i==1)
				{
					response.setContentType("text/html;charset=UTF-8");
			        response.getWriter().write("Success");
				}
				else
				{
					response.setContentType("text/html;charset=UTF-8");
			        response.getWriter().write("No");
				}
			}
			else
			{
				String query1 = "Update rate Set rate='"+rate+"' Where username='"+uid+"' and pid='"+pid+"'";
				int i =db.getUpdate(query1);

				if(i==1)
				{
					response.setContentType("text/html;charset=UTF-8");
			        response.getWriter().write("Success");
				}
				else
				{
					response.setContentType("text/html;charset=UTF-8");
			        response.getWriter().write("No");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
