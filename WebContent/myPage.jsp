<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>main board</title>
	<link rel="stylesheet" type="text/css" href="boardCss.css?ver=11"/></head>
<body>
	<!-- 마이페이지. 개인정보 수정 및 내 게시물, 댓글을 확인 할 수 있다.  -->
	<!-- 맨위 로그아웃, 내정보 부분? -->
	<div class="stateBar">
		<a id="logo" href="board.jsp">로고</a>
		<div id="greeting">${sessionScope.loginedMember.name }님 환영합니다~!</div>
		<div>
			<a href="logoutH" id="logout">로그아웃</a>
			<a href="showingMyPageH" id="myPage">My Page</a>
		</div>
	</div>
	
	<!-- 내 정보 수정하는 부분.  -->
	<div class="myInfo">
		<form action = "updateMyInfoH" method="POST">
			pw 확인 <input type="password" name="checkPw"><br>
			id : ${sessionScope.loginedMember.id }<br>
			pw : <textarea name="inputPw"></textarea><br>
			name : <textarea name="inputName">${sessionScope.loginedMember.name }</textarea><br>
			<input type="text" name="accountNum" value="${sessionScope.loginedMember.num }">
			<input type="submit" value="수정하기">		
		</form>
	</div>

	<!-- 내 게시물 보는 부분. -->
	<div class="myPost">
		<br><br><p>내가 올린 게시글</p>
		<!-- 올린 게시글이 없는 경우. -->
		<c:if test="${sessionScope.myPost==null }">
			올린 게시글이 없습니다.
		</c:if>
		
		<!-- 올린 게시글이 있는 경우. -->
		<c:if test="${sessionScope.myPost!=null }">
			<table>
				<tr>
					<th id="postListNum">번호</th>
					<th id="postListTitle">제목</th>
					<th id="postListId">작성자</th>
					<th id="postListScore">추천</th>
					<th id="postListVoteNum">참여수</th>
					<th id="postListViewCount">조회수</th>
					<th id="postListTime">시간</th>
				</tr>
				<c:forEach var="i" items="${sessionScope.myPost }" varStatus="status">
					<tr>
						<td>${i.num }</td>
						<td><a href="showingPostH?postNum=${i.num}">${i.title }</a></td>
						<td>${i.id }</td>
						<td>${i.score }</td>
						<td>${i.voteNum }</td>
						<td>${i.viewCount }</td>
						<td>${i.time }</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
	<!-- 내 댓글 보는 부분. -->	
	<div class="myComment">
		<p>내가 올린 댓글</p><br>
		<!-- 올린 게시글이 없는 경우. -->
		<c:if test="${sessionScope.myComment==null }">
			올린 댓글이 없습니다.
		</c:if>
		
		<!-- 올린 게시글이 있는 경우. -->
		<c:if test="${sessionScope.myComment!=null }">
			<table>
				<tr>
					<th>게시글 번호</th>
					<th>댓글 번호</th>
					<th>내용</th>
					<th>추천</th>
					<th id="postListVoteNum">참여수</th>
					<th id="postListTime">시간</th>
				</tr>
				<c:forEach var="i" items="${sessionScope.myComment }" varStatus="status">
					<tr>
						<td><a href="showingPostH?postNum=${i.postNum}">${i.postNum }</a></td>
						<td>${i.commentNum }</td>
						<td>${i.content }</td>
						<td>${i.score }</td>
						<td>${i.voteNum }</td>
						<td>${i.time }</td>
						<td>
							<form action = "deleteCommentH" method="POST">
								<input type="text" name="commentNum" value="${i.commentNum }">
								<input type="submit" value="삭제">
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	
	</div>
</body>
</html>