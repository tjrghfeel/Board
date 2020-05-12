package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LogoutH extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession sesseion = request.getSession();
		sesseion.invalidate();
		
		response.sendRedirect("login.html");
	}

}
