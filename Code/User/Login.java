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
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String cat = request.getParameter("cat");
		//System.out.println(cat);
		String subcat = request.getParameter("subcat");
		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		String query = "select * from user where username = '" + username
				+ "' and password = '" + password + "'";
		ResultSet rs = (ResultSet) db.getResultSet(query);
		
	try {
		if(rs.next()){
			String uid=rs.getString("id");
			String adusername=rs.getString("username");
			if(adusername.equals("admin"))
			{
				response.sendRedirect("admin/addproducts.jsp");
			}
			else
			{
				session.setAttribute("uid", uid);
				if(!(cat==null))
				{
					session.setAttribute("cat", cat);
					session.setAttribute("subcat", subcat);
					response.sendRedirect("user/products.jsp");
				}
				else
				{
					response.sendRedirect("user/products.jsp");
				}
			}
        	
		}else{
			out.println("<script type=\"text/javascript\">");
        	out.println("alert('wrong username or password')");
        	out.println("location=\"login.jsp\"");
        	out.println("</script>");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
