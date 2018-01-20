package music;

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
@WebServlet("/mLogin")
public class mLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mLogin() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		
		String query = "select * from user where username = '" + username
				+ "' and password = '" + password + "'";
		ResultSet rs = (ResultSet) db.getResultSet(query);
		
		try 
		{
			if(rs.next()){
				String uid=rs.getString("id");
				String name=rs.getString("name");
				session.setAttribute("uid", uid);
				session.setAttribute("name", name);
				response.sendRedirect("music/home.jsp");
	        	
			}else{
				out.println("<script type=\"text/javascript\">");
	        	out.println("alert('wrong username or password')");
	        	out.println("location=\"index.jsp\"");
	        	out.println("</script>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
