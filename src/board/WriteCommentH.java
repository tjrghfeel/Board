package board;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

//댓글쓰기 처리해주는 서블릿. 
//그냥 입력받은 댓글내용을 db 댓글테이블에 저장해주는 기능. 
public class WriteCommentH extends HttpServlet {
	public void doPost(HttpServletRequest request,  HttpServletResponse response)
	throws IOException, ServletException{
		PreparedStatement ps;
		String query = null;
		HttpSession session = request.getSession();
		String commentContent = request.getParameter("commentContent");
		String id = (String)session.getAttribute("memberId");
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		//int postListIndex = Integer.parseInt(request.getParameter("postListIndex"));
		
		if(session.getAttribute("memberNum")==null) {
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인이 해제되었습니다'); location.href='login.html';</script>");
            out.close();
            return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
            
            if(request.getParameter("commentNum")==null) {//덧글이 아니면,
                query = "insert into comment(postNum,id,content) values(?,?,?)";
                ps = connection.prepareStatement(query);
                ps.setInt(1, postNum);
                ps.setString(2,id);
                ps.setString(3, commentContent);
                ps.executeUpdate();
            }
            else {//덧글이면,
                query = "insert into comment(postNum,id,content,parrentComment,childComment) values(?,?,?,?,?)";
                ps = connection.prepareStatement(query);
                ps.setInt(1, postNum);
                ps.setString(2,id);
                ps.setString(3, commentContent);
                ps.setInt(4, Integer.parseInt(request.getParameter("commentNum")));
                ps.setInt(5, 1);
                ps.executeUpdate();
            }
            connection.close();
		}
		catch(Exception e) {
			System.out.println("error : "+e);
		}
		System.out.println("before sendRedirect");
		response.sendRedirect("showingPostH?&postNum="+postNum);	
	}
}
