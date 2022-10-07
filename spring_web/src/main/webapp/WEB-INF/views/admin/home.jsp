<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<head>
<link rel="stylesheet" href="/css/adminindex.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script>
$(document).ready(function(){
	document.getElementById("id").onfocus = function(e){
		document.getElementById("msg").innerHTML = "";
	}
});
</script>

<script>
function formCheck() {
	var id = document.forms["login"]["id"].value;
	if (id == "") {
		alert("아이디를 입력하세요.");
		return false;
	}

	var pw = document.forms["login"]["pw"].value;
	if (pw == "") {
		alert("비밀번호를 입력하세요.");
		return false;
	} else {
	}
}
</script>
	
</head>

<body>
<form name="loginform" method="post" action="login" onsubmit="return formCheck()">
	<div class="wrap">
		<div class="login">
			<h2>관리시스템</h2>
	
			<div class="login_id">
			<input type="text" name="id" id="id" placeholder="아이디" maxlength="10" required>
			</div>
	            
			<div class="login_pw">
			<input type="password" name="pw" placeholder="비밀번호" minlength="5" maxlength="10" required>
			</div>
			
			<p id="msg">
			<c:if test="${msg != null}"> 회원정보가 없습니다. </c:if>
			</p>
	            
			<div class="submit">
			<input type="submit" value="로그인">
			</div>
		</div>
	</div>
</form>
 
</body>
</html>