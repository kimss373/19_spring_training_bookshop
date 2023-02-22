<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardMain</title>
</head>
<body>	

	<%--
	
		<Resource 
			auth="Container" 
			driverClassName="com.mysql.cj.jdbc.Driver"
			loginTimeout="10" 
			maxWait="5000" 
			name="jdbc/pool" 
			username="root"
			password="1234" 
			type="javax.sql.DataSource"
			url="jdbc:mysql://localhost:3306/JSP_MVC2_EX?serverTimezone=UTC&amp;useSSL=false"
		/> 
	
	 --%>
	<div align="center">
		<img src="img/jsp.PNG" alt="jsp symbol" width="800" height="500"><br>
		<input type="button" value="게시판 보기" onclick="location.href='boardList'">
	</div>
</body>
</html>