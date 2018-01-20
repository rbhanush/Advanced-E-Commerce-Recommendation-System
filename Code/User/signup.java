package User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnection;

/**
 * Servlet implementation class signup
 */
@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signup() {
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
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");                                                                                 
        DatabaseConnection db = new DatabaseConnection();
        db.dbconnection();
        
        if(!(email.equals("")))
        {
	        String queryText = "insert into user(name, house, address, email, mobile, username, password, session) values('"+name+"','no','no','"+ email+"','no','"+email+"','"+ password+"', 'no')";
	
	        int i = db.getUpdate(queryText);
	        if(i==1)
	        {
	        	response.sendRedirect("login.jsp");
	        }
	        else
	        {         
	        	response.sendRedirect("signup.jsp");
	        }
        }
        else
        {
        	String queryText = "insert into user(name, house, address, email, mobile, username, password, session) values('"+name+"','no','no','no','"+ mobile+"','"+mobile+"','"+ password+"', 'no')";
        	
	        int i = db.getUpdate(queryText);
	        if(i==1)
	        {
	        	response.sendRedirect("login.jsp");
	        }
	        else
	        {         
	        	response.sendRedirect("signup.jsp");
	        }
        }
	}

}
