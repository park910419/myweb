package com.syp.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.syp.dto.Criteria;
import com.syp.dto.Reply;
import com.syp.dto.ReplyPageDTO;
import com.syp.dto.ReplyVO;
import com.syp.service.ReplyService;

@RestController
@RequestMapping("/reply/")
public class ReplyController {
	
	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	ReplyService service;
	
	@PostMapping(value="add", 
				 consumes = "application/json",
				 produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> create(@RequestBody Reply reply){
		
		log.info("ReplyController create() called.." + reply);
		int rs = service.register(reply);
		
		return rs == 1 ? new ResponseEntity<>("댓글 등록이 되었습니다", HttpStatus.OK)
					   : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@GetMapping(value="list/{bno}/{page}",
				produces = {MediaType.APPLICATION_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(
						@PathVariable("bno") Long bno,
						@PathVariable("page") int page){
							
		log.info("getList....");
		Criteria cri = new Criteria(page, 5);
		
		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
	}
	
	
	@GetMapping(value="{rno}", 
				produces = {MediaType.APPLICATION_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
		log.info("get reply:" + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="{rno}",
					method = {RequestMethod.PUT, RequestMethod.PATCH},
					produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> modify(@PathVariable("rno") Long rno,
										 @RequestBody ReplyVO vo){
		log.info("rest controller modify() called ===================");
		log.info("rno"+vo.getSeqno());
		log.info("content"+vo.getContent());
		return service.modify(vo) == 1 ? 
				new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK): 
				new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	
	@DeleteMapping(value="{rno}", produces="text/plain; charset=utf-8")
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		log.info("delete rno"+rno);
		return service.remove(rno) == 1?
				new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK): 
				new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
