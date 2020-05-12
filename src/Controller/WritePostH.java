package Controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.util.*;
import DAO.*;
import VO.*;

//작성한 글을 db에 업로드해주는 서블릿. 
public class WritePostH extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		BoardDAO dao = new BoardDAO();
		HttpSession session = request.getSession();
		AccountVO loginedMember = (AccountVO)session.getAttribute("loginedMember");
		String memberId = loginedMember.getId();
		String root = "C:\\Apache\\Apache24\\htdocs";
		int inputImageNum =0;
		String inputImage=null;
		int maxSize = 1024*1024*10; 
		
		MultipartRequest mr = new MultipartRequest(request, root,maxSize,"UTF-8", new DefaultFileRenamePolicy());
		//입력받은 값들을 저장.
		String inputTitle = mr.getParameter("inputTitle");
		String inputContent = mr.getParameter("inputContent");
			//첨부이미지가 있으면 그만큼 반복하여 파일명을 저장.
		while(mr.getFilesystemName("inputImage"+inputImageNum)!=null) {
			//추가한 이미지이름을 세미콜론으로 구분하여 이어붙여 문자열로만들어서 db에 저장할것임. 
			inputImage = inputImage+mr.getFilesystemName("inputImage"+inputImageNum)+";";
			inputImageNum++;
		}
		
		//게시글 db에 저장. 
		dao.writePost(memberId, inputTitle, inputContent, inputImage);

		response.sendRedirect("showingBoardH?inputListIndex=1");
	}

}
