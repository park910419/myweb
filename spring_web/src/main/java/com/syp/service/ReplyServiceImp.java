package com.syp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syp.dto.Criteria;
import com.syp.dto.Reply;
import com.syp.dto.ReplyPageDTO;
import com.syp.dto.ReplyVO;
import com.syp.mapper.ReplyMapper;

@Service
public class ReplyServiceImp implements ReplyService {

	private static final Logger log = LoggerFactory.getLogger(ReplyServiceImp.class);
	
	@Autowired 
	private ReplyMapper mapper;
	
	@Override
	public int register(Reply reply) {
		
		log.info("reply register service called.." + reply);
		return mapper.insert(reply);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		return mapper.getList(cri, bno);
	}

	@Override
	public ReplyVO get(Long rno) {
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}

	@Override
	public int remove(Long rno) {
		return mapper.delete(rno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(
								mapper.getCoungByBno(bno),
								mapper.getList(cri, bno)
								);
	}

}
