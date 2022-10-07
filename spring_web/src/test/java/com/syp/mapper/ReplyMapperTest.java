package com.syp.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.syp.dto.ReplyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyMapperTest {

	private static final Logger log = LoggerFactory.getLogger("ReplyMapperTest.class");
		
	@Autowired
	private ReplyMapper mapper;
	
	/*
	@Test
	public void test() {
		
		Reply r = new Reply();
		r.setComment("안녕하세요");
		r.setBoardNo("1");
		r.setId("young");
		
		mapper.insert(r);
	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria(1, 5);
		List<ReplyVO> list = mapper.getList(cri, 1L);
		for(ReplyVO r : list) {
			log.info("댓글내용:"+r.getContent());
		}
	}
	*/
	
	@Test
	public void testUpdate() {
		ReplyVO vo = new ReplyVO();
		vo.setSeqno(26L);
		vo.setContent("댓글 수정!! happy day!!");
		int count = mapper.update(vo);
		log.info("update count:" + count);
	}
}
