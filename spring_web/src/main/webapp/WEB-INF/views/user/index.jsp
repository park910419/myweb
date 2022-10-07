<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="common.jsp" %>
	<script>
	function init(){
	    var msg = document.getElementsByName("msg");
	    
	    var alert_msg;
	    var modal_pop = false;
	    
	    if(msg[0].value == "loginOk") {
	        alert_msg ="로그인이 되었습니다.";
	        modal_pop = false;
	    }         
         
		if(msg[0].value == "loginFail") {
			alert_msg = "회원 정보가 없습니다.";
			modal_pop = true;
		}
		
		if(msg[0].value == "memberOK") {
			alert_msg = "회원 등록이 되었습니다.";
			modal_pop = true;
		}
		
		if(msg[0].value != "null") alert(alert_msg);
		if(modal_pop) document.getElementById("modal").style.display = "block";
	}	
	</script>
</head>

<body onload="init()">

<input type="text" name="msg" value="<%=request.getAttribute("msg")%>"> 

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<c:set value="${loginUser}" var="loginuser"/>
<p>현재접속자 수 : ${loginUser.getTotal_user() } 명
jsp코드 현재접속자 수: <%-- <%= LoginImpl.total_user %> --%>명


<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Dec 7, 2017</h5>
      <div class="fakeimg" style="height:200px;">Image</div>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Sep 2, 2017</h5>
      <div class="fakeimg" style="height:200px;">Image</div>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
  </div>
  <div class="rightcolumn">
    <div class="card">
      <h2>About Me</h2>
      <div class="fakeimg" style="height:100px;">Image</div>
      <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
    </div>
    <div class="card">
      <h3>Popular Post</h3>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
    </div>
    <div class="card">
      <h3>Follow Me</h3>
      <p>Some text..</p>
    </div>
  </div>
</div>

<%@ include file="footer.jsp" %>

<%@ include file="member/login_modal.jsp" %>

</body>
</html>
    