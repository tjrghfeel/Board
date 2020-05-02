package board;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import board.CommentBean;
import board.PostBean;

//게시글 목록에서 글 누르면 해당 글 보여주기위한 처리 서블릿. 
//해당 글에 대한 내용은 showingBoard서블릿에서 이미 세션속성에 저장되어 있으므로 댓글에대한 db를 가져와 속성에 저장시켜주면 된다. 
public class ShowingPostH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException
	{
		LinkedList<LinkedList<CommentBean>> commentList = new LinkedList<LinkedList<CommentBean>>();//댓글을 저장할 리스트.
		Statement st;
		ResultSet rs;
		String query=null;
		PostBean post = null;//게시글 내용을 담을 빈객체. 
		HttpSession session = request.getSession();
		//String postListIndex = request.getParameter("postListIndex"); //board.jsp에서 누른 게시글의 번호. 단, 게시글의 postNum이 아닌 현재 저장되어있는 세션 속성 배열에서 몇번째 글인지를 나타냄. 
		//System.out.println("postListIndex : "+postListIndex);
		String postNum = request.getParameter("postNum");
		System.out.println("postNum : "+postNum);
		//session.setAttribute("postListIndex", postListIndex);
		
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
            
            //게시글 내용을 보여주기위한 db처리부분. 
            query = "select * from post where num = '"+postNum+"'";//클릭된 post번호에 해당하는 게시글의 데이터를 가져옴. 
            rs = st.executeQuery(query);
            if(rs.next()) {//검색된 row가 있으면, post에 내용을 저장.
            	post = new PostBean(rs.getInt("num"),rs.getString("id"), rs.getString("time"), rs.getString("title"), rs.getString("content"), 
            			rs.getString("image"), rs.getFloat("score"), rs.getInt("voteNum"), rs.getInt("viewCount"));
            	session.setAttribute("post", post);
            }
            else {//검색된 row가 없으면,
            	System.out.println("검색된 row가 없습니다");
            }
            
			//댓글 뿌려주기위한 처리 부분.
            query = "select * from comment where postNum = '"+postNum+"' order by time;";//게시물번호에 해당하는 댓글들을 시간역순으로 검색. 
            rs = st.executeQuery(query);
            System.out.println("before while");
            while(rs.next()){
            	if(rs.getBoolean("childComment")==false) {//댓글이 덧글이 아니라면,
            		commentList.add(new LinkedList<CommentBean>());
            		commentList.getLast().add(new CommentBean(rs.getInt("postNum"),rs.getInt("commentNum"),rs.getString("id"),rs.getString("content"),rs.getString("time"),rs.getFloat("score"),rs.getInt("voteNum"),rs.getInt("parrentComment"),rs.getInt("childComment")));
            		System.out.println("commentContent : "+rs.getString("content"));
            		int rowNum = rs.getRow();
            		int commentNum = rs.getInt("commentNum");
            		rs.first();//resultset의 커서를 다시 처음으로 돌려 덧글들을 검색하도록한다. 
            		while(rs.next()) {//그  아래 나머지 댓글들에 대해,
            			if(rs.getInt("parrentComment")==commentNum) {//덧글대상이 현재의 댓글이면,
            				//해당 댓글의 덧글로 리스트에 추가해준다. 
            				commentList.getLast().add(new CommentBean(rs.getInt("postNum"),rs.getInt("commentNum"),rs.getString("id"),rs.getString("content"),rs.getString("time"),rs.getFloat("score"),rs.getInt("voteNum"),rs.getInt("parrentComment"),rs.getInt("childComment")));
                    		System.out.println("innerCommentContent : "+rs.getString("content"));
            			}
            		}
            		rs.absolute(rowNum);//커서를 다시 덧글들 확인하기 전으로 돌려줌.
            	}//end of if.
            }//end of while
            System.out.println("end of while. and"+commentList);
            //세션에 댓글 리스트를 저장. 
            session.setAttribute("commentList", commentList);
			
			connection.close();
		}
		catch(Exception e) {
			System.out.println("error : "+e);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
		dispatcher.forward(request, response);
	}

}
