<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>

<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>회원가입</h2>
	  <hr>
	  <div class="memregform">
		  <form name="memRegForm" method="post" action="<%=root %>/member/memRegProc.html" onsubmit="return formCheck()">
			<input type="text" placeholder="아이디" maxlength="10" name="id" required>
			<input type="password" placeholder="비밀번호" maxlength="10" name="pw" required>
			<input type="text" placeholder="이름" name="name">
			<span id="msg"></span>
			
			<input type="radio" name="gender"> 여자
			<input type="radio" name="gender"> 남자
				
			<fieldset style="width:70%;">
			<legend>취미</legend>
			<input type="checkbox" name="hobby"> 테니스
			<input type="checkbox" name="hobby"> 드럼
			<input type="checkbox" name="hobby"> 명상
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

<%@ include file="../member/login_modal.jsp" %>

</body>
</html>


    