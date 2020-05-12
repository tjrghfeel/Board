package Controller;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import DAO.BoardDAO;
import VO.*;

//로그인 처리 서블릿. 
//아디있는지, 비번맞는지 확인후 로그인처리. 
public class LoginH extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
    	BoardDAO dao = new BoardDAO();
    	AccountVO account=null;
    	String inputId = request.getParameter("inputId");
        String inputPw = request.getParameter("inputPw");
        HttpSession session = request.getSession();
        
        account = dao.login(inputId,  inputPw);
        
        if(account ==null) {
        	System.out.println("incorrect id,pw");
        	response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println("<script>alert('id가 존재하지 않습니다'); location.href='login.html'; </script>");
        	out.close();
        }
        else {
        	System.out.println("correct id,pw");
        	session.setAttribute("loginedMember", account);
        }
    	
    	//페이지 메인게시판으로 이동.
    	response.sendRedirect("showingBoardH?inputListIndex=1");
    }
}