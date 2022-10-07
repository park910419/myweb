package com.syp.dao;

import java.util.List;
import java.util.Map;

import com.syp.dto.AttachFile;
import com.syp.dto.Board;
import com.syp.dto.Criteria;

public interface BoardDao {

	public List<Board> boardList(Criteria cri);
	
	public Board boardDetail(String seqno);
	
	public String insert(Board board, AttachFile attach);
	
	void insertThumbNail(String attach_no, AttachFile attachFile);
	
	String insertAttachFile(String seqno, AttachFile attachFile);
	
	public void update(Board board, AttachFile attachFile);
	
	public int getTotalRec(Criteria cri);
	
	public Map<String, String> deleteByNo(String seqno);

	public String insertBoard(Board board);
	
}
