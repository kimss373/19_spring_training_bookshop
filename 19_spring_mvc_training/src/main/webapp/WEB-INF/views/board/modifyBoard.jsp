<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyUpdate</title>
<script src="${contextPath }/resources/ckeditor/ckeditor.js"></script>
</head>
<body>
	<div align="center" style="padding-top: 100px">
		<form action="${contextPath }/modifyBoard" method="post" >
			<div align="center">
				<h1>게시글 수정</h1>
				<br>
			</div>
			<table style="width: 700px;" border="1">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<td>작성일</td>
					<td><fmt:formatDate value="${boardDTO.modifyDt}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" name="writer" value="${boardDTO.writer}"/></td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="subject" value="${boardDTO.subject}" size="70"/></td>
				</tr>
				<tr>
					<td>패스워드</td>
					<td><input type="password" name="passwd" size="70"/></td>
				</tr>
				<tr>
					<td>글내용</td>
					<td>
						<textarea rows="10" cols="60" name="content">${boardDTO.content}</textarea>
						<script>CKEDITOR.replace("content");</script>
					</td>
				</tr>
				<tr align="center">
					<td colspan="4">
						<input type="hidden" name="boardId" value="${boardDTO.boardId }">
						<input type="submit" value="글수정" />
						<input type="button" onclick="location.href='${contextPath }/boardList'" value="목록보기" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>