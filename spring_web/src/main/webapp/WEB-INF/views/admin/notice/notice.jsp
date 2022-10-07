<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/dbconn.jsp" %>

<%
String sql = "select * from board";
PreparedStatement stmt = conn.prepareStatement(sql); 
ResultSet rs = stmt.executeQuery();


%>

<%@ include file="/header.jsp" %>

<%@ include file="../menu.jsp" %>


<h1>공지사항</h1>
<a href="#" class="myButton" style="float:right">등록</a>
	
	<table>
	  <tr>
	    <th class="no">순번</th>
	    <th class="title">제목</th>
	    <th class="data">작성일자</th>
	    <th class="click">조회수</th>
	  </tr>
	  
<% while(rs.next()){ %>
	  <tr>
	    <td><%= rs.getInt("idx") %></td>
	    <td><a href="javascript:contents_pop()"><%= rs.getString("title") %></td>
	    <td><%= rs.getString("wdate") %></td>
	    <td>10</td>
	  </tr>
<% } %>	  
	</table>
 	
 	
 <div id="modal">
      <div id="contain">
         <%= rs.getString("contents") %>
         <%= rs.getString("title") %>
      </div>
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
</html>     --%>