<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
	<%--메인 게시판 페이지. 게시글들의 목록을 보여준다. --%>
	<head>
		<meta charset="UTF-8">
		<title>main board</title>
		<link rel="stylesheet" type="text/css" href="boardCss.css?ver=111"/>
	</head>
	<body>
		<!-- 맨위 로그아웃, 내정보 부분? -->
		<div class="stateBar">
			<a id="logo" href="board.jsp">로고</a>
			<div id="greeting">${sessionScope.loginedMember.name }님 환영합니다~!</div>
			<div>
				<a href="logoutH" id="logout">로그아웃</a>
				<a href="showingMyPageH" id="myPage">My Page</a>
			</div>
		</div>
		
		<!-- 본문부분. 게시글목록이 들어갈공간 -->
		<div class="postList">
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
				<c:if test="${sessionScope.postCount >0}">
					<c:forEach var="i" varStatus="a" begin="0" end="${sessionScope.postCount -1}" step="1">
						<tr>
							<td>${sessionScope.postList[i].num }</td>
							<td><a href="showingPostH?postNum=${sessionScope.postList[i].num }">${sessionScope.postList[i].title }</a></td>
							<td>${sessionScope.postList[i].id }</td>
							<td>${sessionScope.postList[i].score }</td>
							<td>${sessionScope.postList[i].voteNum }</td>
							<td>${sessionScope.postList[i].viewCount }</td>
							<td>${sessionScope.postList[i].time }</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		
		<!-- 조작 유틸 부분. 다음페이지, 글쓰기 버튼이 있음. -->
		<div class="postListUtility">
			<!-- 게시글 리스트에서 다음 페이지로 넘기는 부분. -->
			<div class="postListIndex">
				<a href="showingBoardH?inputListIndex=1">맨 처음</a><!-- 맨처음 페이지로 가는버튼. inputListIndex값만 넘겨주며 값은 1이다.  -->
				<!-- 10단위 indexList에서 이전 인덱스묶음?으로 보내 주는부분. 계산을위해 currentListIndex를 넘겨준다. -->
				<a href="showingBoardH?inputListIndex=${sessionScope.currentListIndex-10 }">prev</a>
				<!-- index번호 출력부분. -->
				<c:forEach var="i" begin="${sessionScope.listUtilityBeginIndex+1 }" end="${sessionScope.listUtilityBeginIndex+10 }">
					<c:if test="${sessionScope.listMaxNum>=i }"><!-- index번호가 서블릿에서설정된 최대index번호를 넘지않는다면 출력. -->
						<c:if test="${sessionScope.currentListIndex==i }">
							<a href="showingBoardH?inputListIndex=${i }" id="currentListIndex">${i }</a>
						</c:if>
						<c:if test="${sessionScope.currentListIndex !=i }">
							<a href="showingBoardH?inputListIndex=${i }">${i }</a>
						</c:if>	
					</c:if>			
				</c:forEach>
				<a href="showingBoardH?inputListIndex=${sessionScope.currentListIndex+10 }">next</a><!-- 위와 마찬가지 다음 리스트인덱스묶음으로. -->
				<a href="showingBoardH?inputListIndex=-99">맨 끝</a><!-- inputListIndex값을 -1로 넘기면 맨끝페이지를 가리키는 것으로. -->
			</div>
			<div class="writeButton">
				<a href="writePost.jsp">글쓰기</a>
			</div>
		</div>
		
	</body>
</html>