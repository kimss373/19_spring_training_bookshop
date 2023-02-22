<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
</head>
<body>

	<!-- 
	
		# Excel Export
		
		1) pom.xml파일에 dependency를 추가한다.
	
			<dependency>
			    <groupId>org.apache.poi</groupId>
			    <artifactId>poi</artifactId>
			    <version>3.17</version>
			</dependency>
		
		2) 컨트롤러에서 엑셀 다운로드 기능 구현한다.
		 
		   - 엑셀 다운로드 관련 기능은 구글에서 참조하여 구현한다.
	
	 -->

	<div>
		<img src="${contextPath }/resources/img/member.PNG"/>
	</div>

	<c:choose>
		<c:when test="${sessionScope.memberId eq 'admin'}">
			<p><a href="${contextPath }/member/memberList">회원리스트 조회</a></p>
			<p><a href="${contextPath }/member/logoutMember">로그아웃</a></p>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${sessionScope.memberId eq null}">
					<p><a href="${contextPath }/member/registerMember">회원가입</a></p>
					<p><a href="${contextPath }/member/loginMember">로그인</a></p>	
				</c:when>
				<c:otherwise>
					<p><a href="${contextPath }/member/modifyMember">회원정보 수정</a></p>	
					<p><a href="${contextPath }/member/logoutMember">로그아웃</a></p>
					<p><a href="${contextPath }/member/removeMember">회원탈퇴</a></p>	
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</body>
</html>