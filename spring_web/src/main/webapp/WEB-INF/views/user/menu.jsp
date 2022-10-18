<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${loginUser}" var="loginuser"/>

<div class="topnav">
  <a class="active" href="/">Home</a>
  <a class="board" href="board/list">Board</a>

	<c:if test="${loginuser != null}" >
		<font>${loginuser.name }님 반갑습니다.</font>
		<a class="login" href="logout.do" style="float:right">Logout</a>
    	<a class="mypage" href="member/mypage.jsp" style="float:right">My page</a>
		<a class="chat" href="/chat">chat</a>
	</c:if>
	
	<c:if test="${loginuser eq null }" >
		<a class="login" href="javascript:loginpop()" style="float:right">Login</a>
	</c:if>
	
</div>