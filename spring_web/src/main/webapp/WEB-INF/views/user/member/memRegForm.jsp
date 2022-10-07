<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common.jsp" %>
	<title>세영 홈페이지</title>
</head>

<body>

<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>

<script src="/js/formCheck.js"></script>

<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>${msg}</h2>
	  <hr>
	  <div class="memregform">
		  <form name="memRegForm" method="post" action="/member/register" onsubmit="return formCheck()">
			
			<input type="text" placeholder="아이디" maxlength="10" name="id" required onchange="idCheck()">
			<input type="hidden" id="isIdCheck">
			<p id="idCheckMsg"></p>
			
			<input type="password" placeholder="비밀번호" maxlength="5" name="pw" required>
			
			<input type="text" placeholder="이름" name="name">
			<span id="msg"></span>
			
			<input type="radio" name="gender" value="F"> 여자
			<input type="radio" name="gender" value="M"> 남자
				
			<fieldset style="width:70%;">
				<legend>취미</legend>
				<input type="checkbox" name="hobby" value="운동"> 운동
				<input type="checkbox" name="hobby" value="영화감상"> 영화감상
				<input type="checkbox" name="hobby" value="독서"> 독서
			</fieldset>
			
			<div class="email">
				<input type="text" placeholder="이메일" name="eid">@
				<input type="text" placeholder="도메인주소" name="domain">
				<select name="selDomain" onchange="inputDomain()">
					<option value="">직접입력</option>
					<option value="naver.com">naver.com</option>
					<option value="gmail.com">gmail.com</option>
					<option value="daum.net">daum.net</option>
				</select>
			</div>
			
			<textarea rows="5" cols="50" placeholder="자기소개(최대500자)" name="intro"></textarea>
			
			<input type="submit" value="회원등록">
			<input type="reset" value="취소">		
		  </form>
	  </div>
	  
    </div>
  </div>
</div>

<%@ include file="login_modal.jsp" %>

</body>
</html>


    