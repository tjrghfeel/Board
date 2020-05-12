package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import DAO.*;

public class DeleteCommentH extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		BoardDAO dao = new BoardDAO();
		int commentNum = Integer.parseInt(request.getParameter("commentNum"));
		
		dao.deleteComment(commentNum);
		
		response.sendRedirect("showingMyPageH");
	}
}
