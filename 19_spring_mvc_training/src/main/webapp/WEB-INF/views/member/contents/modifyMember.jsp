<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<title>modify</title>
<script>

	$().ready(function() {
		
		var hp = "${memberDTO.hp}".split("-");
		$("#hp1").val(hp[0]);
		$("#hp2").val(hp[1]);
		$("#hp3").val(hp[2]);
		
		var birthDt = "${memberDTO.birthDt}".split("-");
		$("#birthY").val(birthDt[0]);
		$("#birthM").val(birthDt[1]);
		$("#birthD").val(birthDt[2]);
		
		$("form").submit(function(){
			
			
			$("[name='hp']").val($("#hp1").val() +"-" + $("#hp2").val() +"-" + $("#hp3").val());
			$("[name='birthDt']").val($("#birthY").val() +"-" + $("#birthM").val() +"-" + $("#birthD").val());
			
		});
		
		
	});
	
</script>
</head>
<body>

	<h3>'${memberDTO.memberId }'님의 정보 수정</h3>
	<form action="${contextPath}/member/modifyMember" method="post" enctype="multipart/form-data">
		<table border="1">
	        <tr>
		        <td><img src="${contextPath }/member/thumbnails?fileName=${memberDTO.profile}" width="50" height="50" alt="사진"></td>
		        <td>
		        	<input type="file" name="profile"/>
		        	<input type="hidden" name="beforeFileName" value="${memberDTO.profile}"/>
		        </td>
	        </tr>
			<tr>
				<td>아이디</td>
				<td>
		            <input type="text" name="memberId" id="memberId" maxlength="15" value="${memberDTO.memberId }" readonly/>&emsp;
		        </td>
		    </tr>
	        <tr>
		        <td>비밀번호</td>
		        <td><input type="password" name="passwd" /></td>
	        </tr>
	        <tr>
		        <td>이름</td>
		        <td><input type="text" name="memberNm" maxlength="15" value="${memberDTO.memberNm }"/></td>
	        </tr>                
		    <tr>
		        <td>성별</td>
		        <td>
		        	<input type="radio" name="sex" value="m" <c:if test="${memberDTO.sex eq 'm'}">checked </c:if> />남성&emsp;&emsp;&emsp;
					<input type="radio" name="sex" value="w" <c:if test="${memberDTO.sex eq 'w'}">checked </c:if> />여성
		        </td>
	        </tr>                              
	        <tr>
		        <td>생년월일</td>
		        <td>
	                <select id="birthY" >
						<c:forEach var="i" begin="0" end="2023">
							<option>${2023 - i}</option>
						</c:forEach>
					</select> 년 
					<select id="birthM">
					  	<c:forEach var="i" begin="1" end="12">
						   <c:choose>
								<c:when test="${i < 10 }">
									<option>0${i}</option>
								</c:when>
								<c:otherwise>
									<option>${i}</option>
								</c:otherwise>
							</c:choose>
					  	</c:forEach>
					</select> 월  
					<select id="birthD">
						<c:forEach var="i" begin="1" end="31">
							<c:choose>
								<c:when test="${i < 10 }">
									<option>0${i}</option>
								</c:when>
								<c:otherwise>
									<option>${i}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> 일 &emsp;
					<input type="hidden" name="birthDt" value="${memberDTO.birthDt }">
		        </td>
	        </tr>                        
	        <tr>
		        <td>핸드폰 번호</td>
		        <td>
		        	<select id="hp1" >
						<option>없음</option>
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="019">019</option>
					</select> - 
					<input size="10px" type="text" id="hp2"> - 
					<input size="10px" type="text" id="hp3">
					<input type="checkbox" id="smsstsYn" name="smsstsYn"  value="Y" <c:if test="${memberDTO.smsstsYn == 'Y'}"> checked</c:if>/>
					<input type="hidden" name="hp" value="${memberDTO.hp}">
	                스프링에서 발송하는 SMS 소식을 수신합니다.
		        </td>
	        </tr>                         
	        <tr>
		        <td>이메일</td>
		        <td>
		        	<input type="email" name="email" value="${memberDTO.email}">  
	                <input type="checkbox" id="emailstsYn" name="emailstsYn" value="Y" <c:if test="${memberDTO.emailstsYn == 'Y'}"> checked</c:if>/>
	                스프링에서 발송하는 E-mail을 수신합니다.
		        </td>
	        </tr>                              
	        <tr>
		        <td>주소</td>
		        <td>
		        	<input type="text" placeholder="우편번호 입력" id="zipcode" name="zipcode" value="${memberDTO.zipcode }">
	                <input type="button" onclick="javascript:execDaumPostcode()" value="검색">
	                <br><br>
	                            도로명 주소 : <input type="text" name="roadAddress" id="roadAddress" value="${memberDTO.roadAddress }" size="60"> <br>
					지번 주소 : <input type="text"  name="jibunAddress" id="jibunAddress" value="${memberDTO.jibunAddress }" size="60"> <br>
					나머지 주소: <input type="text" name="namujiAddress" id="namujiAddress" value="${memberDTO.namujiAddress }" size="60"/>
		        </td>
	        </tr>
	        <tr>
		        <td>기타</td>
		        <td>
		        	<textarea rows="10" cols="10" name="etc">${memberDTO.etc }</textarea>
		        	<script>CKEDITOR.replace('etc');</script>
		        </td>
	        </tr>                                     
	        <tr>
		        <td colspan="2" align="center">
		        	<input type="submit" value="수정하기"  >
		        </td>
		    </tr>
	     </table>
     </form>
     <a href="${contextPath }/member/mainMember">메인으로 이동</a>
</body>
</html>