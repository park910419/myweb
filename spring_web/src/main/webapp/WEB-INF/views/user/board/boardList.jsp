<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common.jsp" %>
	<title>세영 홈페이지</title>
	<link rel="stylesheet" href="/css/board.css">
</head>

<body>
<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>

<h1>공지사항</h1>
	<form name="search" method="POST" action="/board/list">
		<input type="hidden" name="currentPage" value="${pageMaker.cri.currentPage}">
		
		<select name="searchField">
		  <option value="title" 
		  <c:if test="${pageMaker.cri.searchField == 'title'}">selected</c:if>>제목</option>		
		  <option value="name"
		  <c:if test="${pageMaker.cri.searchField == 'name'}">selected</c:if>>이름</option>
		</select>
		
		<input type="text" name="searchText" placeholder="search..." 
		value="${pageMaker.cri.searchText}">
		
		<input type="submit" value="검색">
		<!-- <input type="button" value="검색" onclick="document.forms['search'].submit()"> -->
		
		<!-- 페이지당 레코드수 -->
		<select name="rowPerPage" onchange="goAction()" >
			<c:forEach var="i" begin="5" end="40" step="5">
			<option value="${i}"
			 <c:if test="${i == pageMaker.cri.rowPerPage}">selected</c:if>
			 >${i}개씩</option>
			</c:forEach>
		</select>	
	</form>
	<script>
	function goAction(){
		document.forms["search"].submit();
	}
	
	</script>	
					
		<c:if test="${loginuser != null}">
		<input type="button" style="float:right" onclick="location.href='/board/regForm'" value="등록">
		</c:if>

	<table>
	  <tr>
	    <th class="no">순번</th>
	    <th class="title">제목</th>
	    <th class="data">작성일자</th>
	    <th class="click">조회수</th>
	   	<th class="open">작성자</th>	
	  </tr>
		  
	<c:forEach items="${board}" var="board">
	  <tr onclick="location.href='/board/detail?seqno=${board.seqno}'">
	    <td>${board.no}</td>
	    <td>${board.title}</td>
	    <td>${board.wdate}</td>
	    <td>${board.count}</td>
	    <td>${board.name}</td>
	  </tr>
	</c:forEach>
	</table>

 	<p>총 레코드 갯수 : ${pageMaker.total}</p>
 	<div class="pagination">
 		<c:if test="${pageMaker.prev}">
	  	<a href="/board/list?currentPage=${pageMaker.startPage-1}&rowPerPage=${pageMaker.cri.rowPerPage}">&laquo;</a>
	  	</c:if>
	  
	  <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
	  	<a href="/board/list?currentPage=${num}&rowPerPage=${pageMaker.cri.rowPerPage}"
	  	 class="${pageMaker.cri.currentPage == num ? "active" : " " }">${num}</a>
	  </c:forEach>
<!-- 	  <a class="active" href="#">2</a> -->

		<c:if test="${pageMaker.next}">
	  	<a href="/board/list?currentPage=${pageMaker.endPage+1}&rowPerPage=${pageMaker.cri.rowPerPage}">&raquo;</a>
		</c:if>
	</div>
 
 <script>
function contents_pop() {
   document.getElementById("modal").style.display = "block";      
}
function contents_close() {
   document.getElementById("modal").style.display = "none";      
}     
</script>

<%@ include file="../member/login_modal.jsp" %>

</body>
</html>    