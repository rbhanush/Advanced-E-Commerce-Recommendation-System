package admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import database.DatabaseConnection;

/**
 * Servlet implementation class Register
 */
@WebServlet("/insertproduct")
public class insertproduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public insertproduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		DatabaseConnection db = new DatabaseConnection();
		db.dbconnection();
		String path = getServletContext().getRealPath("/admin/product");
		ArrayList<String> textbox = new ArrayList<String>();
		ArrayList<String> textbox1 = new ArrayList<String>();
		try {
			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					String fieldValue = item.getString();
					textbox.add(fieldValue);
				} else {
					String fieldName = item.getFieldName();
					String fileName = FilenameUtils.getName(item.getName());
					InputStream fileContent = item.getInputStream();
					String name = new File(item.getName()).getName();
					textbox1.add(name);
					item.write(new File(path + File.separator + name));
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sql = "INSERT INTO products(pcate,pscate,pname,pprice,pqty,pstock,image1,image2,image3,description,twits) VALUES('"
				+ textbox.get(0)
				+ "','"
				+ textbox.get(1)
				+ "','"
				+ textbox.get(2)
				+ "','"
				+ textbox.get(3)
				+ "','"
				+ textbox.get(4)
				+ "','"
				+ textbox.get(5)
				+ "','"
				+ textbox1.get(0)
				+ "','"
				+ textbox1.get(1)
				+ "','"
				+ textbox1.get(2) + "','" + textbox.get(6) + "','" + textbox.get(7) + "')";
		int i = db.getUpdate(sql);
		if (i == 1) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Products Added Successfully');");
			out.println("location=\"admin/addproducts.jsp\";");
			out.println("</script>");
		} else {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error Occure in Adding');");
			out.println("location=\"admin/addproducts.jsp\";");
			out.println("</script>");
		}
	}

}
