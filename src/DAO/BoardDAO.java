package DAO;

import java.sql.*;

import VO.AccountVO;
import VO.CommentVO;
import VO.PostVO;

public class BoardDAO {
	
	//id, pw에 해당하는 회원정보 검색 메소드. 
	public AccountVO login(String id, String pw) {
		Connection connection;
		Statement st;
		ResultSet rs;
		String query;
		AccountVO vo=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?"
					+ "characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
			
            st = connection.createStatement();
            query = "select * from account where id=\'"+id+"\' and pw = \'"+pw+"\'";
            rs = st.executeQuery(query);
            
            if(rs.next()==false) {
            	System.out.println("in DAO's login(), there is no account. ");
            }
            else if(rs.isLast()==true) {
            	System.out.println("in DAO's login(), there  is account.");
            	vo = new AccountVO();
            	vo.setNum(rs.getInt("num"));
            	vo.setId(rs.getString("id"));
            	vo.setPw(rs.getString("pw"));
            	vo.setName(rs.getString("name"));
            	
            	rs.close();
            	st.close();
            	connection.close();
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}//end of login()
	
	//해당 id가 존재하는지 확인해주는 메소드.
	public AccountVO checkId(String id) {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query =null;
		AccountVO vo = null;
		
		try {
			st = connection.createStatement();
			query = "select * from account where id=\'"+id+"\'";
			rs = st.executeQuery(query);
			if(rs.next()) {
				vo = new AccountVO();
				vo.setNum(rs.getInt("num"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}//end of checkId()
	
	//회원가입처리해주는 메소드.
	public void signUp(String id, String pw, String name) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query=null;
		
		try {
			query = "insert into account(id,pw,name) values(?,?,?)";
			ps = connection.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	//게시글 목록 검색 메소드. 페이지 번호에 해당하는 15개의 post를 반환해준다. 
	public PostVO[] postList(int pageNum) {
		Statement st;
		PreparedStatement ps;
		ResultSet rs;
		String query=null;
		PostVO[] vo =null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
            st = connection.createStatement();
            query = "select * from post order by time desc;";
            rs = st.executeQuery(query);
            
            //pageNum에 해당하는 post구하기. 
            rs.absolute(15*(pageNum-1));
            vo =new PostVO[15];
            for(int i=0; i<15&&rs.next(); i++) {
            	vo[i] = new PostVO();
            	vo[i].setNum(rs.getInt("num"));
            	vo[i].setId(rs.getString("id"));
            	vo[i].setTime(rs.getString("time"));
            	vo[i].setTitle(rs.getString("title"));
            	vo[i].setContent(rs.getString("content"));
            	vo[i].setImage(rs.getString("image"));
            	vo[i].setScore(rs.getFloat("score"));
            	vo[i].setVoteNum(rs.getInt("voteNum"));
            	vo[i].setViewCount(rs.getInt("viewCount"));
            }
            
            rs.close();
            st.close();
            connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}//end of postList()
	
	//총 게시글 수 반환해주는 메소드. 
	public int totalPostCount() {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query=null;
		int totalPostCount=-99;
		
		try {
			query = "select * from post";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			rs.last();
			totalPostCount = rs.getRow();
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return totalPostCount;
	}
          
	//게시글쓰기 메소드.
	public void writePost(String id, String title, String content, String image) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query =null;
		
		try {
			query = "insert into post(id, title, content, image) values(?,?,?,?)";
			ps = connection.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, title);
			ps.setString(3, content);
			ps.setString(4, image);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}

	//게시글 검색 메소드. 
	public PostVO selectPost(int postNum){
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query=null;
		PostVO vo =null;
		
		try {
			query = "select * from post where num =\'"+postNum+"\'";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {
				System.out.println("in selectPost(), there is no post");
			}
			else {
				System.out.println("in selectPost(), there is post");
				vo = new PostVO();
				vo.setNum(rs.getInt("num"));
				vo.setId(rs.getString("id"));
				vo.setTime(rs.getString("time"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setImage(rs.getString("image"));
				vo.setScore(rs.getFloat("score"));
				vo.setVoteNum(rs.getInt("voteNum"));
				vo.setViewCount(rs.getInt("viewCount"));
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	//게시글에 달린 댓글 검색 메소드. 
	public CommentVO[] selectCommentOfPost(int postNum) {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query=null;
		CommentVO[] vo =null;
		int commentCount = -99;
		
		try {
			query = "select * from comment where postNum =\'"+postNum+"\' order by time";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {
				System.out.println("in selectComment(), there is no comment");
			}
			else {
				rs.last();
				commentCount = rs.getRow();
				rs.beforeFirst();
				
				vo = new CommentVO[commentCount];
				
				for(int i=0; rs.next(); i++) {
					vo[i] = new CommentVO();
					vo[i].setPostNum(rs.getInt("postNum"));
					vo[i].setCommentNum(rs.getInt("commentNum"));
					vo[i].setId(rs.getString("id"));
					vo[i].setContent(rs.getString("content"));
					vo[i].setTime(rs.getString("time"));
					vo[i].setScore(rs.getFloat("score"));
					vo[i].setVoteNum(rs.getInt("voteNum"));
					vo[i].setParrentComment(rs.getInt("parrentComment"));
					vo[i].setChildComment(rs.getInt("childComment"));
				}
			}
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	//특정 댓글 검색 메소드. 
	public CommentVO selectComment(int commentNum) {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query =null;
		CommentVO vo=null;
		
		try {
			query = "select * from comment where commentNum =\'"+commentNum+"\'";
			st = connection.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next()==false) {
				System.out.println("in selectComment(), there is no comment");
			}
			else {
				vo = new CommentVO();
				vo.setPostNum(rs.getInt("postNum"));
				vo.setCommentNum(rs.getInt("commentNum"));
				vo.setId(rs.getString("id"));
				vo.setContent(rs.getString("content"));
				vo.setTime(rs.getString("time"));
				vo.setScore(rs.getFloat("score"));
				vo.setVoteNum(rs.getInt("voteNum"));
				vo.setParrentComment(rs.getInt("parrentComment"));
				vo.setChildComment(rs.getInt("childComment"));
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	//게시글 조회수 추가.
	public void updateViewCount(int postNum) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query =null;
		
		try {
			query = "update post set viewCount = viewCount+1 where num =\'"+postNum+"\'";
			ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}

	//게시글 점수 추가
	public void updatePostScore(float score, int postNum) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query = "update post set voteNum = voteNum+1, score = ? "
				+ "where num = \'"+postNum+"\'";
		
		try {
			ps = connection.prepareStatement(query);
			ps.setFloat(1, score);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//댓글 점수 추가
	public void updateCommentScore(float score, int commentNum) {
		Connection connection  = connectionConfig();
		PreparedStatement ps;
		String query=null;
		
		try {
			query = "update comment set voteNum = voteNum+1, score =? where commentNum = \'"+commentNum+"\'";
			ps = connection.prepareStatement(query);
			ps.setFloat(1, score);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//댓글 입력
	public void writeComment(int postNum, String writerId, String content, int parrentComment, int childComment) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query=null;
		
		try {
			query = "insert into comment(postNum, id, content, parrentComment, childComment) values(?,?,?,?,?)";
			ps = connection.prepareStatement(query);
			ps.setInt(1, postNum);
			ps.setString(2, writerId);
			ps.setString(3, content);
			ps.setInt(4, parrentComment);
			ps.setInt(5, childComment);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//게시글 삭제 메소드.
	public void deletePost(int postNum) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query =null;
		
		try {
			query = "delete from post where num=\'"+postNum+"\'";
			ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}

	//댓글 삭제 메소드. 
	public void deleteComment(int commentNum) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query =null;
		
		try {
			query = "delete from comment where commentNum=\'"+commentNum+"\'";
			ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}
	//계정 검색 메소드. 
	public AccountVO selectAccount(int accountNum) {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query=null;
		AccountVO vo=null;
		
		try {
			query = "select * from account where num = \'"+accountNum+"\'";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {
				System.out.println("in selectAccount(), there is no account");
			}
			else {
				vo = new AccountVO();
				vo.setNum(rs.getInt("num"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
			}
			
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	//내 정보 수정 메소드.
	public void updateMyInfo(int num, String pw, String name) {
		Connection connection = connectionConfig();
		PreparedStatement ps;
		String query =null;
		
		try {
			query = "update account set pw = \'"+pw+"\', name=\'"+name+"\' where num=\'"+num+"\'";
			ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}

	//내 게시글 검색 메소드. 
	public PostVO[] selectMyPost(String accountId) {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query=null;
		PostVO[] vo = null;
		
		try {
			query = "select * from post where id =\'"+accountId+"\'";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {//게시글이 없다면,
				System.out.println("in selectMyPost(), there is no post");
			}
			else {//게시글이 있다면,
				System.out.println("in selectMyPost(), there is post");
				rs.last();
				int rowCount = rs.getRow();
				rs.beforeFirst();
				
				vo = new PostVO[rowCount];
				for(int i=0; rs.next(); i++) {
					vo[i] = new PostVO();
					vo[i].setNum(rs.getInt("num"));
					vo[i].setId(rs.getString("id"));
					vo[i].setTime(rs.getString("time"));
					vo[i].setTitle(rs.getString("title"));
					vo[i].setContent(rs.getString("content"));
					vo[i].setImage(rs.getString("image"));
					vo[i].setScore(rs.getFloat("score"));
					vo[i].setVoteNum(rs.getInt("voteNum"));
					vo[i].setViewCount(rs.getInt("viewCount"));
				}
			}
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	//내 댓글 검색 메소드. 
	public CommentVO[] selectMyComment(String accountId) {
		Connection connection = connectionConfig();
		Statement st;
		ResultSet rs;
		String query=null;
		CommentVO[] vo = null;
		
		try {
			query = "select * from comment where id =\'"+accountId+"\'";
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()==false) {//게시글이 없다면,
				System.out.println("in selectMyComment(), there is no post");
			}
			else {//게시글이 있다면,
				System.out.println("in selectMyComment(), there is post");
				rs.last();
				int rowCount = rs.getRow();
				rs.beforeFirst();
				
				vo = new CommentVO[rowCount];
				for(int i=0; rs.next(); i++) {
					vo[i] = new CommentVO();
					vo[i].setPostNum(rs.getInt("postNum"));
					vo[i].setCommentNum(rs.getInt("commentNum"));
					vo[i].setId(rs.getString("id"));
					vo[i].setContent(rs.getString("content"));
					vo[i].setTime(rs.getString("time"));
					vo[i].setScore(rs.getFloat("score"));
					vo[i].setVoteNum(rs.getInt("voteNum"));
					vo[i].setParrentComment(rs.getInt("parrentComment"));
					vo[i].setChildComment(rs.getInt("childComment"));
				}
			}
			rs.close();
			st.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	//db환경설정 메소드. 
	private Connection connectionConfig() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/sample?"
            		+ "characterEncoding=UTF-8&serverTimezone=UTC", "root", "**2TJRGHmysql");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	
	

}
