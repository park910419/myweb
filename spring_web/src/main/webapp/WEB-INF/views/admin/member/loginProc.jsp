<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import = "java.sql.*" %>

<%@ include file="/dbconn.jsp" %>

<%

String id = request.getParameter("id");
String pw = request.getParameter("pw");

String sql = "select * from member where id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);

stmt.setString(1, id);
ResultSet rs = stmt.executeQuery();


if(rs.next()){
	if(rs.getString("pw").equals(pw)){
		session.setAttribute("id", rs.getString("id"));
		session.setAttribute("name", rs.getString("name"));
		session.setAttribute("login_time", session.getCreationTime());
				
		out.println("<script>");
		out.println("alert('로그인 되었습니다.')");
		out.println("location.href='../main.jsp'");
		out.println("</script>");
	} else{
		out.println("<script>");
		out.println("alert('비밀번호가 틀렸습니다.')");
		out.println("location.href='../index.jsp'");
		out.println("</script>");
	} 
}	else{
		out.println("<script>");
		out.println("alert('아이디 정보가 없습니다.')");
		out.println("location.href='../index.jsp'");
		out.println("</script>");
}

stmt.close();
conn.close();
 %> --%>