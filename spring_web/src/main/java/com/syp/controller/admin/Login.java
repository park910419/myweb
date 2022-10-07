package com.syp.controller.admin;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.syp.common.LoginImpl;
import com.syp.dao.MemberDao;

@Controller
public class Login {
	
	private static final Logger log = LoggerFactory.getLogger(Login.class);
	
	@Autowired
	MemberDao dao;
	
	@PostMapping("login")
	public String login(@RequestParam("id") String id, 
						@RequestParam("pw") String pw,
						HttpSession sess, 
						Model model,
						RedirectAttributes rttr) {
		
		Map<String, String> map = dao.loginProc(id, pw);
		String viewPage = null;
		
		System.out.println("로그인확인=" + map.get("login"));
		
		switch(map.get("login")) {
		case "ok" :
			//세션설정
			LoginImpl loginUser = new LoginImpl(id, map.get("name"));
			sess.setAttribute("loginUser", loginUser);
			model.addAttribute("msg", "loginOk");
			viewPage = "main";
			break;
		default : //로그인 실패
			rttr.addFlashAttribute("msg", "loginFail");
			viewPage = "redirect:/admin/";
		}
		
		return viewPage;
	}

}
