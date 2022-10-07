package com.syp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syp.dao.MemberDao;
import com.syp.dao.MemberDaoImp;
import com.syp.dto.Member;

@Service
public class MemberServiceImp implements MemberService {
	
	@Autowired
	private MemberDao md;
	
	/*
	public MemberServiceImp(MemberDao md) {
		this.md = md;
	}*/

	@Override
	public Map<String, String> login(String id, String pw) {
		//MemberDao에 loginProc()메소드 호출
		return md.loginProc(id, pw);
	}

	@Override
	public int insert(Member member) {
		return md.insertMember(member);
	}

	@Override
	public int idDoubleCheck(String id) {
		return md.selectById(id);
	}

	@Override
	public List<Member> list() {
		return md.getMember();
	}

}
