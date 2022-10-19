package com.syp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ChatController {
	
	@GetMapping("/chat")
	public String regForm() {
		return"chat/chat";
	}
	
	@GetMapping("/chatList")
	public String chatList() {
		return"chat/chatList";
	}
	
	@GetMapping("/chat2")
	public String chat2(String chatNo, Model model) {
		System.out.println("채팅방번호:"+chatNo);
		model.addAttribute("",chatNo);
		return"chat/chat2";
	}

}
