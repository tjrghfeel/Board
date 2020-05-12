package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import DAO.*;
import VO.*;

//회원가입 처리해주는 서블릿. 
public class SignUpH extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            String inputId = request.getParameter("inputId");
            String inputPw = request.getParameter("inputPw");
            String inputName = request.getParameter("inputName");
            BoardDAO dao = new BoardDAO();
            
            //이미 아이디가 존재한다면.
            if(dao.checkId(inputId)!=null) {
            	response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('해당 id가 이미 존재합니다'); location.href='login.html';</script>");
                out.close();
            }
            else {
            	dao.signUp(inputId, inputPw, inputName);
            	response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('회원가입이 완료되었습니다'); location.href='signUp.html';</script>");
                out.close();
            }
    }
}