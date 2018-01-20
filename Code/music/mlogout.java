package music;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/mlogout")
public class mlogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		request.getSession().setAttribute("user_name", null);
//		request.getSession().setAttribute("login_name",null);
//		request.getSession().setAttribute("user_name",null);
//		request.getSession().setAttribute("address",null);
//		request.getSession().setAttribute("gender",null);
//		request.getSession().setAttribute("age",null);
//		request.getSession().setAttribute("email",null);
//		request.getSession().setAttribute("mobile",null);
//		
//		request.getSession().setAttribute("user_role", null);
		request.getSession().setAttribute("uid",null);
		
		response.sendRedirect("music/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
