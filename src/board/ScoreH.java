package board;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

//게시글이나 댓글에 score평가하면 계산하여 score를 등록해주는 서블릿. 
//request로 postNum, postListIndex와 댓글에대한 점수인지 게시글에대한 점수인지에 따라 commentScore, postScore 인자가 넘어온다. 
//서블릿완료후 get방식으로 showingPostH로 넘겨줄것이기때문에 postNum, postListIndex를 인자로 넘겨주어야 한다. 
public class ScoreH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		String postNum = request.getParameter("postNum");
		//String postListIndex = request.getParameter("postListIndex");
		HttpSession session = request.getSession();
		
		Statement st;
		ResultSet rs;
		PreparedStatement ps;
		String query=null;
		
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
            st = connection.createStatement();
            
            //댓글에대한 점수인지 게시글에대한 점수인지에 따라 처리.
            if(request.getParameter("postScore")!=null) {//게시글에 대한 점수처리요청이면,
            	float score = (float)Integer.parseInt(request.getParameter("postScore"));
            	
            	query = "select * from post where num = '"+postNum+"'";
            	rs = st.executeQuery(query);
            	rs.next();
            	System.out.println("score, voteNum : "+rs.getFloat("score")+", "+rs.getInt("voteNum"));
            	score = (rs.getFloat("score")*(float)(rs.getInt("voteNum")+1) + score ) / (float)(rs.getInt("voteNum")+2);//새로 계산된score.
            	//조회수에 1을 더해주었는데 처음게시글생성되면 점수 참여자 수가없어도 평점은 3으로 맞추어놓았다. 따라서 0으로 곱하는걸 막기위해 조절한것. 
            	System.out.println(score);
            	score = ((float)((int)(score*100)))/(float)100;
            	System.out.println(score);
            	//조회수에 1을 더해주었는데 처음게시글생성되면 점수 참여자 수가없어도 평점은 3으로 맞추어놓았다. 따라서 0으로 곱하는걸 막기위해 조절한것. 
            	
            	query = "update post set voteNum = voteNum+1, viewCount = viewCount+1, score = ? where num = '"+postNum+"'";//?없이 저렇게 해도 될까?
            	ps = connection.prepareStatement(query);
            	ps.setFloat(1, score);
            	ps.executeUpdate();
            }
            else {//댓글에 대한 점수처리요청이면,
            	float score = (float)Integer.parseInt(request.getParameter("commentScore"));
            	int commentNum = Integer.parseInt(request.getParameter("commentNum"));
            	
            	query = "select * from comment where commentNum = '"+commentNum+"'";
            	rs = st.executeQuery(query);
            	rs.next();
            	System.out.println("score, voteNum : "+rs.getFloat("score")+", "+rs.getInt("voteNum"));
            	score = (rs.getFloat("score")*(float)(rs.getInt("voteNum")+1) + score ) / (float)(rs.getInt("voteNum")+2);//새로 계산된score.
            	//조회수에 1을 더해주었는데 처음게시글생성되면 점수 참여자 수가없어도 평점은 3으로 맞추어놓았다. 따라서 0으로 곱하는걸 막기위해 조절한것. 
            	System.out.println(score);
            	score = ((float)((int)(score*100)))/(float)100;
            	System.out.println(score);
            	
            	query = "update comment set voteNum = voteNum+1, score = ? where commentNum = '"+commentNum+"'";//?없이 저렇게 해도 될까?
            	ps = connection.prepareStatement(query);
            	ps.setFloat(1, score);
            	ps.executeUpdate();
            }
			
			connection.close();
		}
		catch(Exception e) {
			System.out.println("error : "+e);
			e.printStackTrace();
		}
		
		response.sendRedirect("showingPostH?postNum="+postNum);
	}

}
