package Controller;

public class PostBean {
	private int num;
	private String id;
	private String time;
	private String title;
	private String content;
	private String image;
	private float score;
	private int voteNum;
	private int viewCount;
	
	public PostBean() {}
	public PostBean(int num, String id, String time, String title, String content, String image, float score, int voteNum, int viewCount) {
		this.num=num; this.id=id; this.time=time; this.title=title; this.content=content;this.image=image; this.score=score;
		this.voteNum=voteNum; this.viewCount=viewCount;
	}
	
	public void setNum(int num) {this.num=num;	}
	public int getNum() {return num;}
	
	public void setId(String id) {this.id=id;}
	public String getId() {return id;}
	
	public void setTime(String time) {this.time=time;}
	public String getTime() {return time;}
	
	public void setTitle(String title) {this.title=title;}
	public String getTitle() {return title;}
	
	public void setContent(String content) {this.content=content;}
	public String getContent() {return content;}
	
	public void setImage(String image) {this.image=image;}
	public String getImage() {return image;}
	
	public void setScore(float score) {this.score=score;}
	public float getScore() {return score;}
	
	public void setVoteNum(int voteNum) {this.voteNum=voteNum;}
	public int getVoteNum() {return voteNum;}
	
	public void setViewCount(int viewCount) {this.viewCount=viewCount;}
	public int getViewCount() {return viewCount;}
	
}
