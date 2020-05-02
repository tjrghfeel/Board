package board;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
//로그인처리후 게시판을 보여주기위한 db처리하는 서블릿. 
//db의 게시글 테이블의 내용 불러와 속성에 저장하는 작업 수행. 
public class ShowingBoardH extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		Statement st;
		ResultSet rs;
		String query =null;
		HttpSession session = request.getSession();
		//저장할 게시글 목록의 데이터 변수들.
		int[] postNum = new int[15];
		String[] postId = new String[15];
		String[] postTime = new String[15];
		String[] postTitle = new String[15];
		float[] postScore = new float[15];
		int[] postVoteNum = new int[15];
		int[] postViewCount = new int[15];
		int postCount=0;
		//postListUtility 부분에 필요한 변수들.
		int inputListIndex=Integer.parseInt(request.getParameter("inputListIndex"));//board.jsp에서 클릭한 게시글목록번호. 
		int currentListIndex=inputListIndex;//board.jsp에서 사용할 현재 listIndex값을 속성에 저장해두기위한 변수. 
		int listUtilityBeginIndex=-1;//board.jsp의 postListUtility부분의 맨 왼쪽 시작 리스트 인덱스. 
		int rsRowCount=-1;
		int listMaxNum =-1;//게시글목록 리스트의 최대번호.
		
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
            query = "select * from post order by time desc;";
            rs = st.executeQuery(query);
            //currentListIndex에 맞는 게시글 목록 찾아서 저장해주기. 
            	//row수 계산.
            while(rs.next()) {rsRowCount++;}
            rs.first();//커서를 다시 처음으로 돌려줌.
            
            //postListUtility부분 index번호 설정 부분.
            listMaxNum=rsRowCount/15+1;
            session.setAttribute("listMaxNum", listMaxNum);
            System.out.println("listMaxNum : "+listMaxNum);
            
            //postListUtility부분 번호 설정해주는 부분. 
            if(inputListIndex==-99) {inputListIndex=listMaxNum;}
            else if(inputListIndex<=0) {inputListIndex=1;}//prev버튼 등으로 인해 inputListIndex값이 음수일 경우 처리. 
    		else if(inputListIndex>listMaxNum) {inputListIndex = listMaxNum;}
    		listUtilityBeginIndex=(inputListIndex/10)*10;
    		System.out.println("inputListIndex : "+inputListIndex+", listUtilityBeginIndex : "+listUtilityBeginIndex);
    			
    		session.setAttribute("inputListIndex", inputListIndex);
    		session.setAttribute("listUtilityBeginIndex", listUtilityBeginIndex);
    		currentListIndex = inputListIndex;
    		session.setAttribute("currentListIndex", currentListIndex);
    		
            	//index에 맞는 게시글 목록 속성에 저장.
            rs.absolute(15*(inputListIndex-1)+1);//15*(n-1)+1 = 목록의 첫번째 게시글 번호. 여기부터 15개 뽑아서 저장하면 된다.
            rs.previous();
            System.out.println("startPostNum : "+(15*(inputListIndex-1)+1));
            for(int i=0; (i<15)&&(rs.next());i++) {
            	postNum[i] = rs.getInt("num");
            	postId[i] = rs.getString("id");
            	postTime[i] = rs.getString("time");
            	postTitle[i] = rs.getString("title");
            	postScore[i] = rs.getFloat("score");
            	postVoteNum[i] = rs.getInt("voteNum");
            	postViewCount[i] = rs.getInt("viewCount");
            	postCount=i+1;
            }
            session.setAttribute("postNum", postNum);
            session.setAttribute("postId", postId);
            session.setAttribute("postTime", postTime);
            session.setAttribute("postTitle", postTitle);
            session.setAttribute("postScore", postScore);
            session.setAttribute("postVoteNum", postVoteNum);
            session.setAttribute("postViewCount", postViewCount);
            session.setAttribute("postCount", postCount);
            	
            connection.close();
		}
		catch(Exception e) {
			System.out.println("error : "+e);
		}
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("board.jsp");
		dispatcher.forward(request,response);
		
	}//end of doPost
}//end of class
