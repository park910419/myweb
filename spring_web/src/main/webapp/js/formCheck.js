/**
 * 2022년 6월 2일
 * 작성자 : 박세영
 * 내용 : 회원가입 폼 체크
 */
 function idCheck(){
	var id = document.forms["memRegForm"]["id"].value;
	
	var x = new XMLHttpRequest();	
	//전송방식, 요청문서, 비동식 요청
	x.open("GET", "/member/idDoubleCheck?id=" + id, true);
	x.send();
	
	x.onreadystatechange = function(){
	
		var msg = document.getElementById("idCheckMsg");
	//4: request finished and response is ready
		if(x.readyState === 4 && x.status === 200){
			console.log("ok");
			var rsp = x.responseText.trim();
			document.getElementById("isIdCheck").value = rsp; //문자 앞뒤 공백 삭제	
			
			if(rsp == "0"){
				msg.innerText = "사용가능한 아이디입니다.";	
			} else{
				msg.innerText = "중복된 아이디입니다.";
			}	
		} else{
			//오류발생 403, 404
			console.log("서버에러(403,404)");
		}	
	};

} 

 function formCheck(){
	//비밀번호 체크
	var pw = document.forms["memRegForm"]["pw"].value;
	//alert(pw);
	if(pw.length < 5 ){
		alert("비밀번호 문자길이를 확인하세요.");
		return false;
	} 
	
	//이름 체크
	if(document.forms["memRegForm"]["name"].value.length < 1){
		alert("이름을 입력하세요.");
		//document.getElementById("msg").innerHTML = "이름을 입력하세요.";
		return false;
	}
	
	//성별체크
	var gender = document.forms["memRegForm"]["gender"].value;
	if(gender == ""){
		alert("하나를 선택하세요.");
		return false;
	}
	
	//취미체크
	var hobby_length = document.forms["memRegForm"]["hobby"].length;
	//alert(hobby_length);
	for(var i=0; i < hobby_length; i++){
		if(document.forms["memRegForm"]["hobby"][i].checked){
			//alert(i + "번째 취미가 선택되었음");
			console.log(i + "번째 취미가 선택됨");
			break;
		}		
	}
	
	if(i == hobby_length){
		return false;
		}
}


function inputDomain(){
	console.log("도메인선택함");
	var sel = document.forms["memRegForm"]["selDomain"].value;
	console.log("선택 옵션 값 : " + sel);
	document.forms["memRegForm"]["domain"].value = sel;
	
	if(sel !=""){
		document.forms["memRegForm"]["domain"].readOnly = true;
		document.forms["memRegForm"]["domain"].style.backgroundColor='lightgray';
	} else {
		document.forms["memRegForm"]["domain"].readOnly = false;
		document.forms["memRegForm"]["domain"].focus();
		document.forms["memRegForm"]["domain"].style.backgroundColor='white';
	}
}

//로그인 팝업
function loginpop(){
         document.getElementById("modal").style.display="block";
   }
function modal_close(){
      document.getElementById("modal").style.display="none";
}

//게시물 
