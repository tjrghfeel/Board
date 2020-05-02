package board;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

//회원가입 처리해주는 서블릿. 
public class SignUpH extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            String inputId = request.getParameter("inputId");
            String inputPw = request.getParameter("inputPw");
            String inputName = request.getParameter("inputName");

            try{
                PreparedStatement ps;
                Statement statement;
                ResultSet resultSet;
                String query;

                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
                //id중복 확인부분. 
                query = "select * from account";
			
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                while(resultSet.next()){	
                    if(inputId.equals(resultSet.getString("id"))){
                    	response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("<script>alert('해당 id가 이미 존재합니다'); location.href='signUp.html';</script>");
                        out.close();
                        return; 
                    }
                }
                
                //중복이 없으면 회원가입시켜줌. 
                query = "insert into account(id,pw,name) value(?,?,?)";
                ps = connection.prepareStatement(query);
                ps.setString(1,inputId);
                ps.setString(2,inputPw);
                ps.setString(3,inputName);
                ps.executeUpdate();

                connection.close();
            }
            catch(Exception e){
                System.out.println("error : "+e);
            }
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
		dispatcher.forward(request,response);
    }
}