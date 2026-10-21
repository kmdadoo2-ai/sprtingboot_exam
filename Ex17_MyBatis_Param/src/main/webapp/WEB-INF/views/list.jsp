<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>게시판 목록</title>
        <style>
		    table, td { border: 1px solid black; }
		</style>
    </head>
    <body>
		<table style="width: 500px; border-collapse: collapse;">		
		    <tr>
		        <td>번호</td>
		        <td>작성자</td>
		        <td>제목</td>
		        <td>삭제</td>
		    </tr>		
		    <c:forEach items="${list}" var="dto">		
	        <tr>
	            <td>${dto.id}</td>
	            <td>${dto.writer}</td>	
	            <td><a href="view?id=${dto.id}">${dto.title}</a></td>	
	            <td><a href="delete?id=${dto.id}">X</a></td>
	        </tr>		
		    </c:forEach>
		
		</table>
		
		<p><a href="writeForm">글작성</a></p>

     </body>
</html>