package Controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import DAO.BoardDAO;
import VO.*;

//로그인처리후 게시판을 보여주기위한 db처리하는 서블릿. 
//db의 게시글 테이블의 내용 불러와 속성에 저장하는 작업 수행. 
public class ShowingBoardH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		BoardDAO dao = new BoardDAO();
		HttpSession session = request.getSession();
		PostVO[] vo = new PostVO[15];//게시글 페이지 번호에 맞는 게시글객체들이 저장될 배열. 
		
		int postCount=0;//현재 페이지에서 출력될 게시글의 수. 
		//postListUtility 부분에 필요한 변수들.
		int inputListIndex=Integer.parseInt(request.getParameter("inputListIndex"));//board.jsp에서 클릭한 게시글목록번호. 
		int currentListIndex=inputListIndex;//board.jsp에서 사용할 현재 listIndex값을 속성에 저장해두기위한 변수. 
		int listUtilityBeginIndex=-1;//board.jsp의 postListUtility부분의 맨 왼쪽 시작 리스트 인덱스. 
		int totalPostCount=-1;//현재 존재하는 게시글의 총 개수. 
		int listMaxNum =-1;//현재 게시글 총 수에서 가능한 게시글목록 리스트의 최대번호. 20개의 게시글이있으면 한페이지에 15개출력이니까 listMaxNum은 2가된다. 
		
		//세션이만료되어 로그인이 해제되면 처음페이지로 이동시킴. 
		if(session.getAttribute("loginedMember")==null) {
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인이 해제되었습니다'); location.href='login.html';</script>");
            out.close();
            return;
		}
		
        totalPostCount = dao.totalPostCount();//총 게시글의 개수를 구해준다. 
        listMaxNum=totalPostCount/15+1;//가능한 게시글목록 리스트 최대번호를 구해준다. 
        System.out.println("listMaxNum : "+listMaxNum);
        
        //postListUtility부분 번호 설정해주는 부분. 
        if(inputListIndex==-99) {inputListIndex=listMaxNum;}//-99는 마지막페이지를 의미함. 
        else if(inputListIndex<=0) {inputListIndex=1;}//prev버튼 등으로 인해 페이지번호가 음수가되면 1로바꿔줌. 
		else if(inputListIndex>listMaxNum) {inputListIndex = listMaxNum;}//ex) 가능한 페이지번호최대값이 5인데 페이지번호가 8이면 5로바꿔줌.  
		listUtilityBeginIndex=(inputListIndex/10)*10;//ex) 1~10페이지를 출력하는거면 처음번호인 1을 설정. 
		System.out.println("inputListIndex : "+inputListIndex+", listUtilityBeginIndex : "+listUtilityBeginIndex);
		
		//값들 세션에 설정. 
		session.setAttribute("inputListIndex", inputListIndex);
		session.setAttribute("listUtilityBeginIndex", listUtilityBeginIndex);
		currentListIndex = inputListIndex;
		session.setAttribute("currentListIndex", currentListIndex);
        session.setAttribute("listMaxNum", listMaxNum);
		
        //페이지에 맞게 보여줄 게시글객체들을 가져와 세션에 저장시켜줌. 
		vo = dao.postList(inputListIndex);
		session.setAttribute("postList", vo);
		//postCount 계산.
        for(int i=0; i<15; i++) {
        	if(vo[i]==null) {postCount=i;break;}//pageNum에 해당하는 게시글15개를 가져왔는데 다 채워져있지않다면 채워진개수를 postCount값으로할당.
        	else if(i==14) {postCount=15;}
        }
		session.setAttribute("postCount", postCount);

		RequestDispatcher dispatcher = request.getRequestDispatcher("board.jsp");
		dispatcher.forward(request,response);
	}//end of doPost
}//end of class
