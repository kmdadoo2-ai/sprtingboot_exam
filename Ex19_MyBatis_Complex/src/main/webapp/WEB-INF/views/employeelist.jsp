<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>10번 부서 직원 목록</title>
        <style>
            body { font-family: sans-serif; margin: 40px; color: #24352d; }
            table { border-collapse: collapse; width: 100%; max-width: 680px; }
            th, td { border: 1px solid #cad7ce; padding: 12px 16px; text-align: left; }
            th { background: #edf6ef; }
            caption { text-align: left; margin-bottom: 12px; }
        </style>
    </head>
    <body>
        <h1>10번 부서 직원 목록</h1>
        <p>MyBatis로 EMP와 DEPT를 JOIN한 결과입니다.</p>

        <table>
            <caption>직원 이름과 소속 부서</caption>
            <thead>
                <tr>
                    <th scope="col">직원 이름</th>
                    <th scope="col">부서 번호</th>
                    <th scope="col">부서 이름</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dto" items="${employees}">
                    <tr>
                        <td><c:out value="${dto.ename1}" /></td>
                        <td><c:out value="${dto.dno1}" /></td>
                        <td><c:out value="${dto.dname1}" /></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty employees}">
                    <tr><td colspan="3">조회된 직원이 없습니다.</td></tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>