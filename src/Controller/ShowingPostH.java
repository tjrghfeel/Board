package Controller;

import javax.servlet.http.*;

import Controller.PostBean;
import VO.CommentVO;

import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import DAO.*;
import VO.*;

//게시글 목록에서 글 누르면 해당 글 보여주기위한 처리 서블릿. 
//해당 글에 대한 내용은 showingBoard서블릿에서 이미 세션속성에 저장되어 있으므로 댓글에대한 db를 가져와 속성에 저장시켜주면 된다. 
public class ShowingPostH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException
	{
		BoardDAO dao = new BoardDAO();
		CommentVO[] commentVO=null;
		PostVO postVO = null;
		
		LinkedList<LinkedList<CommentVO>> commentList = new LinkedList<LinkedList<CommentVO>>();//댓글을 저장할 리스트.
		HttpSession session = request.getSession();
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		String viewCountCheck = request.getParameter("viewCountCheck");
		
		if(session.getAttribute("loginedMember")==null) {
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인이 해제되었습니다'); location.href='login.html';</script>");
            out.close();
            return;
		}
		
		//게시글 내용을 가져와 세션에 저장. 
		System.out.println("viewCountCheck : "+viewCountCheck);
		if(request.getParameter("viewCountCheck")==null) {System.out.println("add viewCount");dao.updateViewCount(postNum);}//조회수 증가가 이미 이루어졌다면, 더이상 조회수 증가하지않음. 
		postVO = dao.selectPost(postNum);
		session.setAttribute("post", postVO);
		
		//게시글의 댓글 가져와 세션에 저장. 
		//설명 : 댓글 하나와 그에 달려있는 덧글들을 묶어서 하나의 리스트를 만들고, 이를 모든 댓글에 대해 만들것이므로 리스트형의 리스트가 만들어진다. 
		//이를 세션에 저장시켜준다. 
		//우선 덧글이 아닌 댓글을 찾아서, 리스트에 제일 앞에 add하고, parrentComment와 childComment 필드값을 통해 이 댓글에 대한 덧글을 모두 검색하여
		//처음 댓글이 저장된 리스트의 뒤에 이어서 저장해주는 방식으로 댓글과 덧글의 리스트를 만들어준다. 
		commentVO = dao.selectCommentOfPost(postNum);
		if(commentVO!=null) {
			for(int i=0; i<commentVO.length; i++) {//검색된 모든 댓글 객체에 대해 반복. 
				if(commentVO[i].getChildComment()==0) {//덧글이 아닌 댓글이라면,
					commentList.add(new LinkedList<CommentVO>());//리스트를 하나생성하고
					commentList.getLast().add(commentVO[i]);//이 댓글을 먼저 add해준다. 
	        		System.out.println("commentContent : "+commentVO[i].getContent());
	        		
	        		int tempIndex = i;//현재 어디까지 댓글인지 확인했는지를 인덱스를 저장시켜주고,
	        		int commentNum = commentVO[i].getCommentNum();//이 댓글에 대한 덧글을 확인하기위해 댓글번호를 저장해둔다. 
	        		i=0;//이 댓글에 대한 덧글을 확인하기위해 다시 처음부터 검색해야하므로 인덱스를 0으로 만들어주고,
	        		
	        		for( ; i<commentVO.length; i++) {//이 댓글에 대한 덧글 검색 시작. 
	        			if(commentVO[i].getParrentComment()==commentNum){//comment가 parrentComment값으로 위의 댓글을 가진다면,
	        															//위 댓글에 대한 덧글이므로, 
	        				commentList.getLast().add(commentVO[i]);//리스트 뒤에 add.
	        				System.out.println("commentContent : "+commentVO[i].getContent());
	        			}
	        		}//게시글의 모든 commentVO에 대해, 위 댓글에 해당하는 덧글 검색을 완료. 
	        		i=tempIndex;//다시 댓글을 검색하기 위해 인덱스를 돌려놓음. 
				}
			}
		}
		
         /*   System.out.println("before while");
            while(rs.next()){
            	if(rs.getBoolean("childComment")==false) {//댓글이 덧글이 아니라면,
            		commentList.add(new LinkedList<CommentVO>());
            		commentList.getLast().add(new CommentVO(rs.getInt("postNum"),rs.getInt("commentNum"),rs.getString("id"),rs.getString("content"),rs.getString("time"),rs.getFloat("score"),rs.getInt("voteNum"),rs.getInt("parrentComment"),rs.getInt("childComment")));
            		System.out.println("commentContent : "+rs.getString("content"));
            		int rowNum = rs.getRow();
            		int commentNum = rs.getInt("commentNum");
            		rs.first();//resultset의 커서를 다시 처음으로 돌려 덧글들을 검색하도록한다. 
            		while(rs.next()) {//그  아래 나머지 댓글들에 대해,
            			if(rs.getInt("parrentComment")==commentNum) {//덧글대상이 현재의 댓글이면,
            				//해당 댓글의 덧글로 리스트에 추가해준다. 
            				commentList.getLast().add(new CommentVO(rs.getInt("postNum"),rs.getInt("commentNum"),rs.getString("id"),rs.getString("content"),rs.getString("time"),rs.getFloat("score"),rs.getInt("voteNum"),rs.getInt("parrentComment"),rs.getInt("childComment")));
                    		System.out.println("innerCommentContent : "+rs.getString("content"));
            			}
            		}
            		rs.absolute(rowNum);//커서를 다시 덧글들 확인하기 전으로 돌려줌.
            	}//end of if.
            }//end of while
            */
        System.out.println("end of while. and"+commentList);
        //세션에 댓글 리스트를 저장. 
        session.setAttribute("commentList", commentList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
		dispatcher.forward(request, response);
	}

}
