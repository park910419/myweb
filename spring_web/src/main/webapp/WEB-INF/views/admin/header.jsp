<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
/* 	System.out.println(root);
	out.println(root); */
	System.out.println("stat값:" + request.getParameter("stat"));

%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
		<link rel="stylesheet" href="<%=root %>/css/main.css">
		<link rel="stylesheet" href="<%=root %>/css/notice.css">
	<script>
	function init(){
		var stat = document.getElementsByName("stat");
		if(stat[0].value == 1) {
			/* alert(stat[0].value); */
		    document.getElementById("modal").style.display = "block";
		}
	}	
	</script>
</head>

<body onload="init()">
<input type="hidden" id="stat" name="stat" value="<%= request.getParameter("stat")%>">

<div class="header">
  <h1>관리자 시스템</h1>
  <h2>psy_admin</h2>
</div>
