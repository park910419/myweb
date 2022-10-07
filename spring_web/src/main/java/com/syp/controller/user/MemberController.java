package com.syp.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.syp.dto.Member;
import com.syp.service.MemberService;
import com.syp.service.MemberServiceImp;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//MemberService ms = new MemberServiceImp();
	@Autowired
	private MemberService ms;
	
	/*
	MemberController(MemberService ms){
		this.ms = ms;
	}
	*/
	
	@RequestMapping("memRegForm")
	public void memRegForm() {
		logger.info("회원가입폼 맵핑");
	}
	
	@PostMapping("register")
	public String register(Member member) {
		logger.info("회원가입처리 맵핑");
		
		logger.info("아이디:{}",member.getId());
		logger.info("이름:{}",member.getName());
		
		ms.insert(member);
		
		return "redirect:/";
	}
	
	@GetMapping("idDoubleCheck")
	public ResponseEntity<String> idDoubleCheck(@RequestParam("id") String id){
		logger.info("idDoubleCheck called..");
		
		//MemberService ms = new MemberServiceImp();
		String rs = Integer.toString(ms.idDoubleCheck(id));
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
	}
	

}
