package com.syp.service;

import java.util.List;

import com.syp.dto.Criteria;
import com.syp.dto.Reply;
import com.syp.dto.ReplyPageDTO;
import com.syp.dto.ReplyVO;

public interface ReplyService {

	public int register(Reply reply);

	public List<ReplyVO> getList(Criteria cri, Long bno);

	public ReplyPageDTO getListPage(Criteria cri, Long bno);
	
	public ReplyVO get(Long rno);

	public int modify(ReplyVO vo);

	public int remove(Long rno);

	
}
