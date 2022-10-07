<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

	<h1>boardDetail</h1>

	<c:set value="${board}" var="board" />
	<c:set value="${loginUser}" var="user" />
	
	<c:if test="${user.id eq board.id }">
		<button class="myButton" onclick="location.href='/board/detail?seqno=${board.seqno}&page=modify'">수정</button> 
		<button class="myButton" style="float:right" onclick="del_confirm('${board.seqno}')">삭제</button>
	</c:if>
	
	<script>
	function del_confirm(seqno){
		var rs = confirm('정말로 삭제하시겠습니까?');
		if(rs){
			location.href = "boardDelete.bo?seqno=" + seqno;
		}
	}
	</script>
	
	<table>
		<tr><th colspan="3">${board.title}</th></tr>
		<tr>
		<td>${board.wdate}</td>
		<td>${board.name}</td>
		<td>${board.count}</td>
		</tr>
		<tr><td colspan="3">${board.content}</td></tr>
	</table>
	
	<!--첨부파일-->
	<div>
	<c:set value = "${board.attachFile}" var="attachfile" />
	<c:if test="${attachfile != null}">
		<c:forEach items="${attachfile}" var="file">
		
			<form name="filedown" method="post" action="/file/fileDown">
			<input type="hidden" name="filename" value="${file.fileName}">
			<input type="hidden" name="savefilename" value="${file.saveFileName}">
			<input type="hidden" name="filepath" value="${file.filePath}">
			<input type="hidden" name="filesize" value="${file.fileSize}">
			
				<c:set value="${file.fileType}" var="filetype" />
				<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype, '/'))}" var="type" />
				
				<c:if test="${type eq 'image'}">
					<c:set value="${file.thumbnail.fileName}" var="thumb_file" />
					<img src="/upload/thumbnail/${thumb_file}">
				</c:if>
				
				${file.fileName} (사이즈:${file.fileSize} bytes)
				<input type="submit" value="다운로드">
			</form>
		</c:forEach>
	</c:if>
	</div>
	
	<!-- 댓글 등록 폼-->
	<div id="replyInput">
		<textarea id="comment" name="comment" placeholder="댓글작성" rows="2" cols="50"></textarea>
		<button id="addReplyBtn">댓글등록</button>	
	</div>
	
	<!-- 댓글 리스트 출력 영역 -->
	<div id="reply-list">
		<ul class="reply_ul">
			<li data-rno='26'>
				<div>작성자 | 작성일 | 댓글내용</div>
			</li>
		</ul>
	</div>
	
	<!-- 댓글 페이지 리스트 출력 -->
	<div class="reply-page-list">
	
	</div>
	
	<div class="reply">
		<c:set value="${board.reply}" var="reply"/>
		<c:if test="${reply != null}">
			<c:forEach items="${reply}" var ="r">
				${r.comment}
				${r.id}
				${r.wdate}
				<hr>
			</c:forEach>
		</c:if>	
	</div>
	
<%@ include file="../member/login_modal.jsp" %>

<!-- 댓글 모달 -->
<div id="reply_modal">
	<div class="modal-content">
		<div class="container">
			<h2>댓글</h2>
			<button id="modalCloseBtn" style="float:right">&#10062;</button>
			<textarea name="content"></textarea>
			<button id="replyModifyBtn">댓글수정</button>	
			<button id="replyDeleteBtn">댓글삭제</button>	
		</div>
	</div>	
</div>

<script type="text/javascript" src="/js/reply.js"></script>
<script>
/*즉시실행함수(IIFE)
(function(){
	문장
})();
*/
var seqno = '<c:out value="${board.seqno}" />';
var id = '<c:out value="${user.id}" />';

$(document).ready(function(){
	console.log(replyService);
	
	console.log("============");
	console.log("Reply get LIST");
	
	var modal = $("#reply_modal");
	var modal_content = modal.find("textarea[name='content']");
	var currentPage = 1;
	
	modal.hide();
	
	showList(1);
	
	/*모달 닫기 버튼*/
	$("#modalCloseBtn").on("click", function(e){
		modal.hide();
	});
	
	$(".reply_ul").on("click", "li", function(e){
		
		var rno = $(this).data("rno");
		
		replyService.get(rno, function(data){
			console.log("REPLY GET DATA");
			console.log("댓글 " + rno + "번내용:" + data.content);
			modal_content.val(data.content);
			modal.data("rno", data.seqno);
		});
		modal.show();
	});
	
	
	/*댓글 등록 버튼*/
	$("#addReplyBtn").on("click", function(e){
		var comment = document.getElementById("comment").value;
		
		var reply = {
				boardNo:seqno,
				id:id,
				comment:comment
		};
		
		replyService.add(reply, function(result){
			alert("댓글이 등록되었습니다."+result);
			document.getElementById("comment").value = "";
			//document.getElementById("newLine").innerHTML = "<li>" + reply.comment + "</li>";
			showList(-1);
		});
	});
	
	/*댓글 수정 버튼 클릭 시*/
	$("#replyModifyBtn").on("click", function(e){
		console.log("댓글 수정 번호:" + modal.data("rno"));
		console.log("댓글 수정 내용:" + modal_content.val());
		
		var reply = {seqno : modal.data("rno"),
					 content : modal_content.val()};
		
		replyService.update(reply, function(result){
			alert(result);
			modal.hide();
			showList(1);
		});
	});
	
	/*댓글 삭제버튼 클릭 시*/
	$("#replyDeleteBtn").on("click", function(e){

		var rno = modal.data("rno");
		
		console.log("댓글 삭제 번호:" + modal.data("rno"));
		
		replyService.remove(rno, function(result){
			alert(result);
			modal.hide();
			showList(1);
		});
	});

	
	function showList(page){
		replyService.getList({bno:seqno, page:page || 1}, function(replyCnt, list){
			
			console.log("댓글수:"+replyCnt);
			
			/*댓글이 새롭게 등록된 경우*/
			if(page == -1){
				currentPage = Math.ceil(replyCnt/5.0);
				showList(currentPage);
				return;
			}
			
			/*댓글이 없는 경우*/
			if(list == null || list.length == 0){
				$(".reply_ul").html("");
				return;
			}
			
			/*댓글이 있는 경우*/
			var str="";
			for(var i=0, len=list.length || 0; i < len; i++){
				console.log(list[i]);
				str += "<li data-rno='" + list[i].seqno + "'><div class='replyRow'>" + list[i].rn + " | " + list[i].id;
				str += " | " + list[i].wdate + " | " + list[i].content + "</div></li>"
			}
			
			$(".reply_ul").html(str);
			
			showReplyPage(replyCnt);
		});
	}
	
	//showReplyPage(18);
	
	/*댓글 페이지 리스트 출력*/
	function showReplyPage(replyCnt){
		
		//var currentPage = 1;
		
		var endPage = Math.ceil(currentPage/5.0)*5;
		var startPage = endPage - 4;
		console.log("endPage:"+endPage);
		
		var prev = startPage != 1;
		var next = false;
		
		if(endPage * 5 >= replyCnt){
			endPage = Math.ceil(replyCnt/5.0);
		}
		if(endPage * 5 < replyCnt){
			next = true;
		}
		
		var str = "<ul class='pageUL'>";
		if(prev){
			str += "<li class'page-link'>";
			str += "<a href='" + (startPage-1) + "'> 이전</a></li>";
		}
		
		for(var i=startPage; i<=endPage; i++){
			var active = currentPage == i ? "active" : "";
			str += "<li class='page-link " + active + "'>";
			str += "<a href='" + i + "'>" + i + "</a></li>";
		}
		
		if(next){
			str += "<li class='page-link'>";
			str += "<a href='" + (endPage+1) + "'> 다음</a></li>";
		}
		
		str += "</ul>";
		console.log(str);
		$(".reply-page-list").html(str);
	}
	
	/*댓글 페이지번호 클릭 시 댓글 리스트 출력*/
	$(".reply-page-list").on("click", "li a", function(e){
		console.log("page click==========");
		e.preventDefault(); //a태그를 눌러도 href 링크로 이동하지 않게
		var clickPage = $(this).attr("href");
		console.log("currentPage:" + clickPage);
		currentPage = clickPage;
		showList(currentPage);
	});
});

</script>

</body>
</html>    