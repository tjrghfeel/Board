package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import DAO.*;
import VO.*;

public class ShowingMyPageH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		BoardDAO dao = new BoardDAO();
		PostVO[] post;
		CommentVO[] comment;
		HttpSession session = request.getSession();
		AccountVO loginedMember = (AccountVO)session.getAttribute("loginedMember");
		String loginedMemberId = loginedMember.getId();
		
		post = dao.selectMyPost(loginedMemberId);
		comment = dao.selectMyComment(loginedMemberId);
		
		session.setAttribute("myPost", post);
		session.setAttribute("myComment", comment);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("myPage.jsp");
		dispatcher.forward(request, response);
	}
}
