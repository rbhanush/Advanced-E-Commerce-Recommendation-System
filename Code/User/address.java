package User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseConnection;

/**
 * Servlet implementation class signup
 */
@WebServlet("/address")
public class address extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public address() {
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
		HttpSession session=request.getSession();
		String uid =(String) session.getAttribute("uid");
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address"); 
        String pincode = request.getParameter("pincode");
        DatabaseConnection db = new DatabaseConnection();
        db.dbconnection();
	        String queryText = "insert into deliverdata(uid, name, email, mobile, address, items, amt, tax, pay, pin) values('"+uid+"','"+name+"','"+ email+"','"+mobile+"','"+address+"','no','no','no','no','"+pincode+"')";
	
	        int i = db.getUpdate(queryText);
	        if(i==1)
	        {
	        	response.sendRedirect("user/transaction.jsp");
	        }
	        else
	        {         
	        	response.sendRedirect("user/address.jsp");
	        }
	}

}
