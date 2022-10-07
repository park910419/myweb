<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	doA
</h1>
아이디: ${member.id}<br>
이름: ${member.name}<br>
게시물제목: ${board.title}<br>

메세지: ${msg}

</body>
</html>
