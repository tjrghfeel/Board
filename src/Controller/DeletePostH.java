package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import DAO.*;
import VO.*;

public class DeletePostH extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		BoardDAO dao = new BoardDAO();
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		
		dao.deletePost(postNum);
		
		response.sendRedirect("showingBoardH?inputListIndex=1");
	}

}
