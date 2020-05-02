package board;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

public class NoName extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		PreparedStatement ps;
		String query =null;
		HttpSession session = request.getSession();
		Random rd  = new Random();
		String id = "aaa";
		String title = null;
		String content = null;
		
		//db업로드.
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
            
            for(int i=0;i<33;i++) {
            	query = "insert into post(id, title, content) values(?,?,?)";
                ps = connection.prepareStatement(query);
                ps.setString(1,id);
                ps.setString(2, Integer.toString(rd.nextInt(1000)));
                ps.setString(3, Integer.toString(rd.nextInt(1000)));
                ps.executeUpdate();
                Thread.sleep(1000);
            }

            connection.close();
		}
		catch(Exception e) {
			System.out.println("error : "+e);
		}
		RequestDispatcher dispatcher  = request.getRequestDispatcher("board.jsp");
		dispatcher.forward(request, response);
	}
	
	


}
