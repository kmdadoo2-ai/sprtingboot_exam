<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>FileUpload</title>
	</head>
	<body>
		<h2>파일 업로드</h2>
		
		<form action="uploadOk" method="post" enctype="multipart/form-data">		
		    파일 : <input type="file" name="filename"> <br />		
		    <input type="submit" value="File Upload">		
		</form>
		<br />	<br />	
		<form action="/uploadMultiple" method="post" enctype="multipart/form-data">
		    <!-- 반드시 multiple 속성이 있어야 합니다! -->
		    파일 : <input type="file" name="filename" multiple><br><br>
		    <button type="submit">여러 파일 업로드 하기</button>
		</form>
	
	</body>
</html>