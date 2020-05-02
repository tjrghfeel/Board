package board;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class NoName2 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		String aaa = request.getParameter("aaa");
		String bbb = request.getParameter("bbb");
		
		System.out.println("aaa : "+aaa+", bbb : "+bbb);
	}

}
