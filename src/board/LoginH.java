package board;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

//로그인 처리 서블릿. 
//아디있는지, 비번맞는지 확인후 로그인처리. 
public class LoginH extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        Statement st;
        ResultSet rs;
        String query = null;
        
        String inputId = request.getParameter("inputId");
        String inputPw = request.getParameter("inputPw");
        
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
            
            //id확인 부분. 
            query = "select * from account where id = \'"+inputId+"\'";
            st = connection.createStatement();
            rs = st.executeQuery(query);
            //id없으면 알림창과함께 로그인페이지로 돌려보냄. 
            if(rs.next()==false) {
            	response.setContentType("text/html; charset=UTF-8");
            	PrintWriter out = response.getWriter();
            	out.println("<script>alert('id가 존재하지 않습니다'); location.href='login.html'; </script>");
            	out.close();
            	return;
            }
            //id있으면, pw확인.
            if(inputPw.equals(rs.getString("pw"))) {
            	HttpSession session = request.getSession();
            	session.setAttribute("memberNum", rs.getInt("num"));//primary key인 num을 세션에 저장하여 로그인처리. 
            	session.setAttribute("memberName", rs.getString("name"));
            	session.setAttribute("memberId", rs.getString("id"));
            }
            connection.close();
    	}
    	catch(Exception e) {
    		System.out.println("error : "+e);
    	}
    	
    	//페이지 메인게시판으로 이동.
    	response.sendRedirect("showingBoardH?inputListIndex=1");
		/*
		 * RequestDispatcher dispatcher = request.getRequestDispatcher("showingBoardH");
		 * dispatcher.forward(request, response);
		 */
    	
    }
}