package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import DAO.BoardDAO;
import VO.*;

//게시글이나 댓글에 score평가하면 계산하여 score를 등록해주는 서블릿. 
//request로 postNum, postListIndex와 댓글에대한 점수인지 게시글에대한 점수인지에 따라 commentScore, postScore 인자가 넘어온다. 
//서블릿완료후 get방식으로 showingPostH로 넘겨줄것이기때문에 postNum, postListIndex를 인자로 넘겨주어야 한다. 
public class ScoreH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		BoardDAO dao = new BoardDAO();
		PostVO post = null;
		CommentVO comment =null;
		
		//String postListIndex = request.getParameter("postListIndex");
		HttpSession session = request.getSession();
    	int postNum = Integer.parseInt(request.getParameter("postNum"));
		
		if(session.getAttribute("loginedMember")==null) {
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인이 해제되었습니다'); location.href='login.html';</script>");
            out.close();
            return;
		}
		
        //댓글에대한 점수인지 게시글에대한 점수인지에 따라 처리.
        if(request.getParameter("postScore")!=null) {//게시글에 대한 점수처리요청이면,
        	post = dao.selectPost(postNum);
        	float inputScore = (float)Integer.parseInt(request.getParameter("postScore"));
        	float resultScore;
        	int voteNum = post.getVoteNum();
        	float preScore = post.getScore();
        	
        	System.out.println("score, voteNum : "+preScore+", "+voteNum);
        	resultScore = (preScore*(float)(voteNum+1) + inputScore ) / (float)(voteNum+2);//새로 계산된score.
        	//조회수에 1을 더해주었는데 처음게시글생성되면 점수 참여자 수가없어도 평점은 3으로 맞추어놓았다. 따라서 0으로 곱하는걸 막기위해 조절한것. 
        	System.out.println(resultScore);
        	resultScore = ((float)((int)(resultScore*100)))/(float)100;
        	System.out.println(resultScore);
        	//조회수에 1을 더해주었는데 처음게시글생성되면 점수 참여자 수가없어도 평점은 3으로 맞추어놓았다. 따라서 0으로 곱하는걸 막기위해 조절한것. 
        	
        	dao.updatePostScore(resultScore, postNum);
        }
        else {//댓글에 대한 점수처리요청이면,
        	float inputScore = (float)Integer.parseInt(request.getParameter("commentScore"));
        	int commentNum = Integer.parseInt(request.getParameter("commentNum"));
        	comment = dao.selectComment(commentNum);
        	float preScore = comment.getScore();
        	int voteNum = comment.getVoteNum();
        	float resultScore;
        	
        	System.out.println("score, voteNum : "+preScore+", "+voteNum);
        	resultScore = (preScore*(float)(voteNum+1) + inputScore ) / (float)(voteNum+2);//새로 계산된score.
        	//조회수에 1을 더해주었는데 처음게시글생성되면 점수 참여자 수가없어도 평점은 3으로 맞추어놓았다. 따라서 0으로 곱하는걸 막기위해 조절한것. 
        	System.out.println(resultScore);
        	resultScore = ((float)((int)(resultScore*100)))/(float)100;
        	System.out.println(resultScore);
        	
        	dao.updateCommentScore(resultScore, commentNum);
        }
        
		response.sendRedirect("showingPostH?viewCountCheck=1&postNum="+postNum);
	}

}
