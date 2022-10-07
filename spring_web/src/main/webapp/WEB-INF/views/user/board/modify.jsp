<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common.jsp" %>
	<link rel="stylesheet" href="/css/board.css">
	<title>세영 홈페이지</title>
</head>

<body>

<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>

  <h1>boardModify</h1>
  
  	<c:set value="${board}" var="board" />
  	<form method="post" enctype="multipart/form-data" action="/modify">
		<input type="hidden" name="seqno" value="${board.seqno}">
		<table> 
		<tr>
		<th>제목</th>
		<td><input type="text" style="width:100%" name="title" value="${board.title}"></td>
		</tr>
		<tr>
		<th>내용</th>
		<td><textarea style="width:100%" cols="100" rows="30" name="content">${board.content}</textarea></td>
		</tr>
		<tr>
		<th>공개여부</th>
		<td>
		<input type="radio" name="open" value="Y" 
		<c:if test="${board.open == 'Y'}">checked </c:if> >공개
		<input type="radio" name="open" value="N" 
		<c:if test="${board.open == 'N'}">checked </c:if> >비공개
		</td>
		</tr>	 
		</table>
	
		<!-- 첨부파일 -->
		<c:set value="${board.attachFile}" var="attachfile" />
		<c:choose>
			<c:when test="${empty attachfile}">
		 		<input type="file" name="filename">
		 	</c:when>
		 	<c:when test="${!empty attachfile}">
		 		<c:forEach items="${attachfile}" var="file" >
		 			<c:set value="${file.fileType}" var="filetype" />
		 			<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype, '/'))}" var="type" />
			 			<div id="fileSector">
			 			<c:if test="${type eq 'image'}">
			 				<c:set value="${file.thumbnail.fileName}" var="thumb_file" />
			 				<img src="/upload/thumbnail/${thumb_file}">
			 			</c:if>
			 			${file.fileName}(사이즈:${file.fileSize})
			 			<input type="button" value="삭제" onclick="fileDel('${file.no}','${file.saveFileName}','${file.filePath}','${thumb_file}' )">
			 			</div>
		 		</c:forEach>
		 	</c:when>
		 </c:choose>
		
		 <input type="submit" class="myButton" value="수정">
		 <input type="reset" class="myButton" value="취소">
	</form>

<script>
function fileDel(no, savefilename, filepath, thumb_filename){	
	
	 var ans = confirm("정말로 삭제하시겠습니까?");
	 if (ans){
		var x = new XMLHttpRequest();
		x.onreadystatechange = function(){
			 if(x.readyState === 4 && x.status === 200){
				 
				 var tag = document.getElementById("fileSector");
				
				 if(x.responseText.trim() === "0"){
					 alert("파일 삭제를 실패하였습니다.");
				 } else {
					 alert("파일을 삭제하였습니다.")
					 tag.innerHTML = "<input type='file' name='filename'>";
				 }
				 
				 } else {
					 console.log('에러코드:'+x.status);
				 }
		};
	}
	 
	 x.open("POST", "/file/fileDel", true);
	 x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	 x.send("no="+no+"&savefilename="+savefilename+"&filepath="+filepath+"&thumb_filename="+thumb_filename);
}

</script>


<%@ include file="../member/login_modal.jsp" %>
</body>
</html>  