<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id="modal">
	<div class="modal-content">
		<div class="container">
		<form name="loginform" method="post" action="/member/loginProc.jsp">
				<p><a href="javascript:modal_close()" style="float:right">닫기</a><br>
			<input type="text" id="id" placeholder="제목" maxlength="10" name="id" required><br>
			<input type="password" placeholder="내용" maxlength="4" name="pw" required><br>
		</form>
		</div>
	</div>	
</div>
<script>
/* function loginpop() {
    document.getElementById("modal").style.display = "block";
} */
function modal_close() {
    document.getElementById("modal").style.display = "none";
}
</script>
    