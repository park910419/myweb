<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script> 
</head>
<body>

<div class="chat_title">채팅</div>
<div class="chat_search">
<input type="text" placeholder="검색">
</div>

<div>
	<ul class="chat_list">
		<li data-rno="1">방1</li>
		<li data-rno="2">방2</li>
	</ul>
</div>

<script>
	$(".chat_list").on("click","li",function(e){
		var rno = $(this).data("rno");
		console.log("방번호:"+rno);
		location.replace("/chat2?chatNo="+rno);
	});


</script>

</body>
</html>