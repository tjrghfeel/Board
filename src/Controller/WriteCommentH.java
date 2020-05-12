package Controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import DAO.*;
import VO.*;

//댓글쓰기 처리해주는 서블릿. 
//그냥 입력받은 댓글내용을 db 댓글테이블에 저장해주는 기능. 
public class WriteCommentH extends HttpServlet {
	public void doPost(HttpServletRequest request,  HttpServletResponse response)
	throws IOException, ServletException{
		BoardDAO dao = new BoardDAO();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memberId");
		String commentContent = request.getParameter("commentContent");
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		
		if(session.getAttribute("loginedMember")==null) {
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인이 해제되었습니다'); location.href='login.html';</script>");
            out.close();
            return;
		}
            
        if(request.getParameter("commentNum")==null) {//덧글이 아닌 댓글이면, 
        	dao.writeComment(postNum, id, commentContent, 0, 0);
        }
        else {//덧글이면,
        	int parrentComment = Integer.parseInt(request.getParameter("commentNum"));
        	dao.writeComment(postNum, id, commentContent, parrentComment, 1);
        }
        
		response.sendRedirect("showingPostH?viewCountCheck=1&postNum="+postNum);	
	}
}
