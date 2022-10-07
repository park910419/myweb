<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common.jsp" %>
	<title>세영 홈페이지</title>
	<link rel="stylesheet" href="/css/board.css">
</head>

<body>

<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>

  <h1>게시물 등록</h1>
  
  	<form method="post" enctype="multipart/form-data" action="/board/register" onsubmit="return check(this)">
	<table> 
		 <tr>
		 <th>제목
		 <td><input type="text" style="width:100%" name="title">
		 <tr>
		 <th>내용
		 <td><textarea style="width:100%" cols="100" rows="30" name="content"></textarea>
		 <tr>
		 <th>공개여부
		 <td>
		 <input type="radio" name="open" value="Y"> 공개
		 <input type="radio" name="open" value="N"> 비공개
		 <input type="file" name="filename">
		 <tr>
		 <th colspan="2">
		 <input type="submit" class="myButton" value="등록">
		 <input type="reset" class="myButton" value="취소">
	</table>
	</form>

	
<script>
function check(f){
	if(f.open.value==""){
		alert("공개여부를 체크하세요");
		return false;
	} 
	return true;	
}
</script>

<%@ include file="../member/login_modal.jsp" %>
</body>
</html>  