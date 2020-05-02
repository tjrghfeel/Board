<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%--글쓰기 버튼을 누르면들어오는페이지. 게시글쓰기 기능. --%>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="boardCss.css"/>
</head>
<body>
	<%--글쓰기 부분. --%>
	<div class="writePost">
		<form action = "writePostH" method="POST" enctype="Multipart/form-data">
			<table>
				<tr>
					<td>제목</td>
					<td><textarea name="inputTitle"></textarea></td>
				</tr>
				<tr>
					<td>내용</td>
					<td>
						<textarea name="inputContent"></textarea>
					</td>
				</tr>
				<tr>
					<td>첨부파일</td>
					<td>
						<input type="file" name="inputImage1"><br>
						<button type="button" id="moreImage">추가</button>
					</td>
				</tr>
			</table>
			<input type="submit" value="올리기">
		</form>
	</div>
	
	<%--첨부파일 추가버튼을 누르면 더 첨부할수있도록 처리.  --%>
	<script src="http://code.jquery.com/jquery-latest.js"></script>	
	<script>
		var imageCount =2;
		$("#moreImage").bind("click", function(){
			$("#moreImage").before("<input type='file' name='inputImage"+imageCount+"'><br>");<%--이런식으로 문자열 중간에 자바처럼 변수삽입하는게 된다.--%>
			imageCount++;
		})
	
	</script>
</body>
</html>