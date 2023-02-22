<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>add board</title>
<script src="${contextPath }/resources/ckeditor/ckeditor.js"></script>
</head>
<body>
	<div align="center" style="padding-top: 100px">
		<form action="${contextPath }/addBoard" method="post" >
			<div  align="center">
				<h2>게시글 쓰기</h2>
				<br>
			</div>
			<table border="1" style="width:600px;" >
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<td align="center">작성자</td>
					<td><input type="text" name="writer" size="70"/></td>
				</tr>
				<tr >
					<td align="center">제목</td>
					<td><input type="text" name="subject" size="70"/></td>
				</tr>
				<tr>
					<td align="center">비밀번호</td>
					<td><input type="password" name="passwd" size="70"/></td>
				</tr>
				<tr>
					<td align="center">글내용</td>
					<td>
						<textarea  rows="10" cols="50" name="content" ></textarea>
						<script>CKEDITOR.replace("content")</script>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input type="submit" value="글쓰기" />
						<input type="reset"  value="다시작성" />
						<input type="button" onclick="location.href=''" value="목록보기">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>