package board;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.util.*;

//작성한 글을 db에 업로드해주는 서블릿. 
public class WritePostH extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		PreparedStatement ps;
		String query =null;
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("memberId");
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
			inputImage = inputImage+mr.getFilesystemName("inputImage"+inputImageNum)+";";
			inputImageNum++;
		}
		
		//db업로드.
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
            query = "insert into post(id, title, content, image) values(?,?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1,memberId);
            ps.setString(2, inputTitle);
            ps.setString(3, inputContent);
            ps.setString(4, inputImage); 
            ps.executeUpdate();
            
            connection.close();
		}
		catch(Exception e) {
			System.out.println("error : "+e);
		}

		RequestDispatcher dispatcher  = request.getRequestDispatcher("showingBoardH?inputListIndex=1");
		dispatcher.forward(request, response);
	}

}
