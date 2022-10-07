package com.syp.dao;

import java.util.List;
import java.util.Map;

import com.syp.dto.Member;

public interface MemberDao {
	
	public Map<String, String> loginProc(String id, String pw);
	
	public int insertMember(Member member);
	
	public int selectById(String id);
	
	public List<Member> getMember();
	
}
