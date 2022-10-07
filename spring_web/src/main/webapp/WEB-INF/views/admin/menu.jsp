<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String id = (String)session.getAttribute("id");
	String name = (String)session.getAttribute("name");
	boolean isLogin = false;
	if(name != null){
		isLogin = true;
		
	}
%>


<div class="topnav">
  <a class="active" href="/psy_admin/main.jsp">Home</a>
  <a class="board" href="notice/notice.jsp">공지사항</a>
  
 
<% if(isLogin) {%>
		<a class="login" href="/psy_admin/member/logout.jsp" style="float:right">Logout</a>
    	<a style="float:right"><%=name %> (<%= id %>)</a>
<% } %>
</div>