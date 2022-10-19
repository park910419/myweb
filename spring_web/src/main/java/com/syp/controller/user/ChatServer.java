package com.syp.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/chatServer")
public class ChatServer {
	private static final Logger log = LoggerFactory.getLogger(ChatServer.class);

	//연결된 모든 session 저장
	private static Map<String, Session> userMap = new HashMap<>();
	
	//방에 들어온 방번호, userid 저장
	private static Map<String, List<String>> chatUser = new HashMap<>();
	
	//클라이언트가 연결 요청시
	@OnOpen
	public void open(Session session) {
		log.info("Open session id:"+session.getId()+",session:"+session);
	}
	
	//클라이언트가 메시지 전송시
	@OnMessage
	public void handleMessage(Session session, String message) throws IOException {
		log.info("handleMessage:" + message);
		
		JSONObject obj = new JSONObject(message);
		
		String step = obj.getString("step");
		String chatNo = obj.getString("chatNo");
		String userid = obj.getString("userid");
		String msg = obj.getString("msg");
		String txt = null;
		
		//log.info("step:"+step+",chatNo:"+chatNo+",userid:"+userid+",msg:"+msg);
		
		switch(step) {
		case "enter": //클라이언트 대화방 입장
			userMap.put(userid, session);
			if(chatUser.get(chatNo)==null) {
				List<String> list = new ArrayList<>();
				chatUser.put(chatNo, list);
			}
			chatUser.get(chatNo).add(userid);
			log.info("["+userid+"]입장");
			
			txt="["+userid+"]입장했습니다.";
			
			break;
		case "chat":
			txt = "["+userid+"]" + msg;
			break;
		case "out":
			chatUser.get(chatNo).remove(userid);
			userMap.remove(userid);
			log.info(userid+"님 퇴장");
			txt="["+userid+"]퇴장했습니다.";
			break;
		}
		
		//클라이언트에 메시지 전송
		List<String> chatUserList = chatUser.get(chatNo);
		for(int i=0; i<chatUserList.size(); i++) {
			log.info(chatNo+"에 접속자 리스트---------");
			log.info("아이디:"+chatUserList.get(i));
			log.info("세션:"+userMap.get(chatUserList.get(i)).getId());
			log.info("-----------------------------");
			userMap.get(chatUserList.get(i)).getBasicRemote().sendText(txt);
		}
		
	}
	
	//세선연결종료
	@OnClose
	public void onClose(Session session) {
		log.info("session onClose:"+session);
	}
	
	@OnError
	public void onError(Throwable e, Session session) {
		log.info("에러발생:"+e.getStackTrace());
	}
}
