<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script src="/js/formCheck.js"></script>

<div id="modal">
	<div class="modal-content">
		<div class="container">
			<form name="loginform" method="post" action="/login">
				<a href="javascript:modal_close()" style="float:right">닫기</a><br><br>
				<input type="text" id="id" placeholder="아이디" maxlength="10" name="id" required><br><br>
				<input type="password" placeholder="비밀번호" maxlength="10" name="pw" required><br><br>
				<input type="submit" value="로그인">
			</form><br><br><br>
	        	<a href="/member/memRegForm">회원가입</a> | <a href="#">비밀번호 찾기</a>
		</div>     
    </div>
</div>	


    