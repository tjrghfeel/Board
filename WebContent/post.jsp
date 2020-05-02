<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<%--게시글의 내용을 보여주는 jsp페이지.  --%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="boardCss.css?ver=111"/>
	</head>
	<body>
		<!-- 맨위 로그아웃, 내정보 부분? -->
		<div class="stateBar">
			<a id="logo" href="board.jsp">로고</a>
			<div id="greeting">${sessionScope.memberName }님 환영합니다~!</div>
			<div>
				<a href="logoutH" id="logout">로그아웃</a>
				<a href="myPageH" id="mypage">My Page</a>
			</div>
		</div>
		
		<%--게시글 내용 보여주는 부분 --%>
		<div class="post">
			<table>
				<tr><td colspan="3">제목 : ${sessionScope.post.title }</td></tr>
				<tr>
					<td>id : ${sessionScope.post.id }</td>
					<td>score : ${sessionScope.post.score }</td>
					<td>time : ${sessionScope.post.time }</td>
				</tr>
				<tr>
					<td id="postContent" colspan="3">${sessionScope.post.content }</td>
				</tr>
				<tr>
					<td id="postScore" colspan="3">
						<form action="scoreH" method="GET">
							<label><input type="radio" name="postScore" value="1">1점</label>
							<label><input type="radio" name="postScore" value="2">2점</label>
							<label><input type="radio" name="postScore" value="3">3점</label>
							<label><input type="radio" name="postScore" value="4">4점</label>
							<label><input type="radio" name="postScore" value="5">5점</label><br>
							<input type="text" name="postNum" value="${sessionScope.post.num }">
							<input type="submit" value="제출">
						</form>
					</td>
				</tr>
			</table>
		</div>
		
		<%--댓글쓰는 부분. 처음엔 안보이게 해두었다가 버튼누르면 작성폼이 보이도록. --%>
		<div class="topComment">
			<p>댓글쓰기</p>
			<form action = "writeCommentH" method="POST">
				<textarea name="commentContent"></textarea><br>
				<input type="text" name="postNum" style="display:none" value="${sessionScope.post.num}">
				<input type="submit" value="작성하기">
			</form>
		</div>
		
		<%--댓글 보여주는 부분 --%>
		<div class="comment">
			<%--'댓글' 목록 뿌려주는 부분. --%>
			<c:forEach var="item" varStatus = "st" items="${sessionScope.commentList }">
				<table class="mainComment">
					<tr>
						<td>id : ${item[0].id }</td>
						<td class="commentTime">${item[0].time }</td>
					</tr>
					<tr><td class="commentContent">${item[0].content }<td></tr>
					<tr>
						<td>
							score : ${item[0].score }
						</td>
						<td>voteNum : ${item[0].voteNum }</td>
						<td>
							<form action="scoreH" method="GET">
								<label><input type="radio" name="commentScore" value="1">1점</label>
								<label><input type="radio" name="commentScore" value="2">2점</label>
								<label><input type="radio" name="commentScore" value="3">3점</label>
								<label><input type="radio" name="commentScore" value="4">4점</label>
								<label><input type="radio" name="commentScore" value="5">5점</label>
								<input type="text" name="postNum" value="${item[0].postNum }">
								<input type="text" name="commentNum" value="${item[0].commentNum }">
								<input type="submit" value="점수주기">
							</form>
						</td>
					</tr>
				</table>
				<div class="writeInnerComment">
					<button type="button">덧글달기</button><%--이버튼을 누르면 덧글다는 폼이 보이도록. --%>
					<form action="writeCommentH" method="POST">
						<input type="text" name="commentNum" value="${item[0].commentNum }">
						<textarea name="commentContent"></textarea><br>
						<input type="text" name="postNum" style="display:none" value="${sessionScope.post.num }">
						<input type="submit" value="덧글달기">
					</form>
				</div>
				<button type="button" class="showInnerComment">덧글 보기</button><%--이버튼 누르면 해당 댓글의 덧글을 볼 수 있도록 --%>
				
				<%--'덧글' 목록 뿌려주는 부분. --%>
				<div class="innerCommentSection">
					<c:if test="${item[1] != null }">
						<c:forEach var="innerItem" items="${item }" begin="1">
							<table class="innerComment">
								<tr>
									<td>id : ${innerItem.id }</td>
									<td class="commentTime">${innerItem.time }</td>
								</tr>
								<tr><td class="commentContent">${innerItem.content }</td></tr>
								<tr>
									<td>
										score : ${innerItem.score }
									</td>
									<td>voteNum : ${innerItem.voteNum }</td>
									<td>
										<form action="scoreH" method="GET">
											<label><input type="radio" name="commentScore" value="1">1점</label>
											<label><input type="radio" name="commentScore" value="2">2점</label>
											<label><input type="radio" name="commentScore" value="3">3점</label>
											<label><input type="radio" name="commentScore" value="4">4점</label>
											<label><input type="radio" name="commentScore" value="5">5점</label>
											<input type="text" name="postNum" value="${innerItem.postNum }">
											<input type="text" name="commentNum" value="${innerItem.commentNum }">
											<input type="submit" value="점수주기">
										</form>
									</td>
								</tr>
							</table>
						</c:forEach>
					</c:if>	
				</div>
			</c:forEach>
		</div>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>		
		<script>
			$('.writeInnerComment button').bind('click', function(e){/*이벤트 객체 e*/
				e.target.nextSibling.nextSibling.style.display = 'block';/*target이 이벤트가 발생한 요소(여기선 클릭이 발생한 button요소), nextSibling속성이
				바로 다음 형제노드를 가리키는 값. 여기선 button다음의 텍스트값. nextSibling한번 더하여 form을 가리킴. style.display가 form의 css값 display를 가리킴.*/
 			})
 			$('.showInnerComment').bind('click',function(e){
 				$(e.target).next('div.innerCommentSection').css('display','block');
 			})
		
		</script>
	</body>
</html>