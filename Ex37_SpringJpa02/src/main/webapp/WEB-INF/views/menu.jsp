<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<%
		    out.println("Spring JPA #02");
		%>
		<br><p>
		
		<a href=/insert>데이터 추가</a> <br><p>
		<a href=/selectAll>전체 조회</a> <br><p>
		<a href=/selectById?id=1>개별 조회 - byId</a> <br><p>
		<hr>
		<a href=/selectByName?name=을지문덕>개별 조회 - byName</a> <br><p>
		<a href=/selectByEmail?email=test7@test.com>개별 조회 - byEmail</a> <br><p>
		<a href=/selectByNameLike?name=김>리스트 조회 - Name Like</a> <br><p>
		<a href=/selectByNameLikeNameDesc?name=김>리스트 조회 - Name Like Name Desc</a> <br><p>
		<hr>
	
	</body>
</html>