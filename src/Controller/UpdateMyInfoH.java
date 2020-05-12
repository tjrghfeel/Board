package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import DAO.*;
import VO.*;

public class UpdateMyInfoH extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		BoardDAO dao = new BoardDAO();
		int accountNum = Integer.parseInt(request.getParameter("accountNum"));
		String checkPw = request.getParameter("checkPw");
		String inputPw = request.getParameter("inputPw");
		String inputName = request.getParameter("inputName");
		AccountVO account = dao.selectAccount(accountNum);
		
		//비번확인이 틀렸으면 페이지를 돌려보냄. 
		if(account.getPw().equals(checkPw)==false) {
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('pw가 틀렸습니다'); location.href='showingMyPageH';</script>");
            out.close();
		}
		
		//내정보 수정. 
		dao.updateMyInfo(accountNum, inputPw, inputName);
		
		response.sendRedirect("showingMyPageH");
	}

}
