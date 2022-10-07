package com.syp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syp.dto.Criteria;
import com.syp.dto.Reply;
import com.syp.dto.ReplyVO;

public interface ReplyMapper {

	public int insert(Reply reply);

	public List<ReplyVO> getList(
			@Param ("cri") Criteria cri, 
			@Param ("bno") Long bno);

	public ReplyVO read(Long rno);

	public int update(ReplyVO vo);

	public int delete(Long rno);
	
	public int getCoungByBno(Long bno);
}
