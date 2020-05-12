package VO;

public class CommentVO {
	private int postNum;
	private int commentNum;
	private String id;
	private String content;
	private String time;
	private float score;
	private int voteNum;
	private int parrentComment;
	private int childComment;
	
	public CommentVO() {}
	public CommentVO(int postNum, int commentNum, String id, String content, String time, float score, int voteNum, int parrentComment, int childComment) {
		this.postNum = postNum; this.commentNum=commentNum; this.id=id; this.content=content; this.time=time; this.score=score; this.voteNum=voteNum;
		this.parrentComment=parrentComment; this.childComment=childComment;
	}
	public void setPostNum(int postNum) {this.postNum=postNum;}
	public int getPostNum() {return this.postNum;}
	public void setCommentNum(int num) {this.commentNum = num ;}
	public int getCommentNum() {return this.commentNum;}
	public void setId(String id) {this.id = id ;}
	public String getId() {return this.id  ;}
	public void setContent(String content) {this.content = content ;}
	public String getContent() {return this.content  ;}
	public void setTime(String time) {this.time = time ;}
	public String getTime() {return this.time  ;}
	public void setScore(float score) {this.score =score  ;}
	public float getScore() {return this.score  ;}
	public void setVoteNum(int num) {this.voteNum = num ;}
	public int getVoteNum() {return this.voteNum  ;}
	public void setParrentComment(int num) {this.parrentComment =  num;}
	public int getParrentComment() {return this.parrentComment  ;}
	public void setChildComment(int a) {this.childComment = a ;}
	public int getChildComment() {return this.childComment  ;}
}
