<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>입력 폼</title>
        <style>
		    table, td { border: 1px solid black; }
		</style>
    </head>
    <body>
    	<br/><p/>
    	<form action="write" method="post">
			<table style="width: 500px; border-collapse: collapse;">				
			    <tr> 
			        <td>작성자</td>
			        <td><input type="text" name="writer" size="100"></td>
			    </tr>
			
			    <tr>
			        <td>제목</td>
			        <td><input type="text" name="title" size="100"></td>
			    </tr>
			
			    <tr>
			        <td>내용</td>
			        <td><input type="text" name="content" size="100"></td>
			    </tr>
			
			    <tr>
			        <td colspan="2"><input type="submit" value="입력">
			            &nbsp;&nbsp; <a href="list">목록보기</a></td>
			    </tr>							
			</table>
		</form>	
     </body>
</html>