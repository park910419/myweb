package com.syp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.syp.common.OracleConn;
import com.syp.dto.AttachFile;
import com.syp.dto.Board;
import com.syp.dto.Criteria;
import com.syp.dto.Reply;
import com.syp.dto.Thumbnail;


public class BoardDao_SQL {
	
	private final Connection conn = OracleConn.getInstance().getConn();
	
	public List<Board> boardList(Criteria cri) {
		
		List<Board> board = new ArrayList<Board>();
		
		String sql = "SELECT * FROM (";
			   sql += " SELECT rownum as rn, seqno, title, wdate, count, name";
			   sql += " FROM (SELECT seqno, title,";
			   sql += " TO_CHAR(b.wdate, 'YYYY-MM-DD HH24:MI:SS') wdate,";
			   sql += " count, name";
			   sql += " FROM board b, member m ";
			   sql += " WHERE b.id = m.id";
			   sql += " )";
			   sql += " WHERE rownum <= ?*? ORDER BY seqno DESC ";
			   sql += " ) WHERE 1=1 ";
			   sql += " and rn > (?-1)*?"
			   		+ "";
			   PreparedStatement stmt;

		try {
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
											  ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setInt(3, cri.getCurrentPage());
			stmt.setInt(4, cri.getRowPerPage());
			ResultSet rs = stmt.executeQuery();
			
//			rs.last();
//			board = new Board[rs.getRow()];
//			rs.beforeFirst();
			
//			int i=0;
			while(rs.next()){
				Board b = new Board();
				
				b.setNo(rs.getString("rn"));
				b.setSeqno(rs.getString("seqno"));
				b.setTitle(rs.getString("title"));
				b.setWdate(rs.getString("wdate"));
				b.setName(rs.getString("name"));
				b.setCount(rs.getString("count"));
//				board[i++] = b;
				board.add(b);
			}
			
			//보드
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return board;
	}

	public Board boardDetail(String seqno) {
		
		Board board = new Board(); 
		
		try {
			//조회수 카운트
			String sql = "update board set count = count+1 where seqno = " +seqno;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			
			//해당 게시물 테이블 내용
			sql = " select title, b.content, b.id, open, "; 
			sql += " 		TO_CHAR(b.wdate, 'YYYY-MM-DD HH24:MI:SS') wdate, ";
			sql += " 		count, (select name from member m where m.id =b.id) name ";
			sql += " from board b ";
			sql += " where b.seqno = ? ";
			sql += " union all";
			sql += " select '', r.content, r.id, '', "; 
			sql += " 		TO_CHAR(r.wdate, 'YYYY-MM-DD HH24:MI:SS'), ";
			sql += " 		0, (select name from member m where m.id =r.id) ";
			sql += " from reply r ";
			sql += " where r.board_seqno = ? ";	
			
/*			sql = "select title t, b.content, id, ";
			sql += " TO_CHAR(b.wdate, 'YYYY-MM-DD HH24:MI:SS') board_wdate,";
			sql += " count c,";
			sql += " (select name from member m where m.id =b.id) name";
			sql += " from board b";
			sql += " where b.seqno = ?";*/
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					  						  ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, seqno);
			stmt.setString(2, seqno);
			ResultSet rs = stmt.executeQuery();
			
			
			//게시글
			if(rs.next()){
				board.setSeqno(seqno);
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setId(rs.getString("id"));
				board.setWdate(rs.getString("wdate"));
				board.setCount(rs.getString("count"));
				board.setName(rs.getString("name"));	
				board.setOpen(rs.getString("open"));
						
				//댓글	
	//			rs.last(); 
	//			reply = new Reply[rs.getRow()-1];
	//			rs.beforeFirst();
	//			rs.next();
				List<Reply> reply = new ArrayList<Reply>();
				
	//			int num=0; 
				while(rs.next()){ 
					Reply r = new Reply();
					r.setComment(rs.getString("content")); 
					r.setId(rs.getString("id"));
					r.setWdate(rs.getString("wdate")); 
	//				reply[num++] = r; 
					reply.add(r);
					
				} 
				
				board.setReply(reply);
				
				//첨부파일 저장
				sql = "SELECT * FROM attachfile WHERE board_seqno = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, seqno);
				rs = stmt.executeQuery();
				
				List<AttachFile> fileList = new ArrayList<AttachFile>();
				
				while(rs.next()) {
					AttachFile attachfile = new AttachFile();
					attachfile.setNo(rs.getString("no"));
					attachfile.setFileName(rs.getString("filename"));
					attachfile.setSaveFileName(rs.getString("savefilename"));
					attachfile.setFileSize(rs.getString("filesize"));
					attachfile.setFileType(rs.getString("filetype"));
					attachfile.setFilePath(rs.getString("filepath"));
					
					if(rs.getString("filetype").contains("image")) {
						sql = "SELECT * FROM attachfile_thumb WHERE attach_no = ? ";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, rs.getString("no"));
						ResultSet rs2 = stmt.executeQuery();
						
						while(rs2.next()) {
							Thumbnail th = new Thumbnail();
							th.setNo(rs2.getString("no"));
							th.setFileName(rs2.getString("filename"));
							th.setFileSize(rs2.getString("filesize"));
							th.setFilePath(rs2.getString("filepath"));
							attachfile.setThumbnail(th);
						}
					}
					fileList.add(attachfile);
				}	
				
			board.setAttachFile(fileList);
//			System.out.println(reply[1].getComment());
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
//		System.out.println(board.getReply()[1].getComment());
		return board;
	}

	public String insert(Board board, AttachFile attachFile) {
		
		String sql = "INSERT INTO board(seqno, title, content, open, id) VALUES (BOARD_SEQ.NEXTVAL, ?, ?, ?, ? )";
		PreparedStatement stmt;
		String seqno = null;
		
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getContent());
			stmt.setString(3, board.getOpen());
			stmt.setString(4, board.getId());
			stmt.executeQuery();
			
			sql = "SELECT max(seqno) as seqno FROM board";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			seqno = rs.getString("seqno");
			
			//첨부파일
			if(attachFile != null) {
				String attach_no = insertAttachFile(seqno, attachFile);
				String fileType = attachFile.getFileType();
				
				//썸네일
				if(fileType.substring(0, fileType.indexOf("/")).equals("image")) {
					insertThumbNail(attach_no, attachFile);
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("rollback 처리됨");
			}
			e.printStackTrace();
		}
		
		return seqno;
	}
	
	void insertThumbNail(String attach_no, AttachFile attachFile) {
		//썸네일 저장
		String sql = "INSERT INTO attachfile_thumb(no, filename, filesize, filepath, attach_no)"
			+ "VALUES(ATTACHFILE_THUMB_SEQ.NEXTVAL, ?, ?, ?, ?)";
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql);
				Thumbnail thumb = attachFile.getThumbnail();
				stmt.setString(1, thumb.getFileName());
				stmt.setString(2, thumb.getFileSize());
				stmt.setString(3, thumb.getFilePath());
				stmt.setString(4, attach_no);
				stmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	String insertAttachFile(String seqno, AttachFile attachFile) {
		String sql = "INSERT INTO attachFile(no, filename, savefilename, filesize, filetype, filepath, board_seqno)"
				+ "VALUES (ATTACHFILE_SEQ.NEXTVAL, ?,?,?,?,?,?)";
			PreparedStatement stmt;
			String attach_no = null;
			try {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, attachFile.getFileName());
				stmt.setString(2, attachFile.getSaveFileName());
				stmt.setString(3, attachFile.getFileSize());
				stmt.setString(4, attachFile.getFileType());
				stmt.setString(5, attachFile.getFilePath());
				stmt.setString(6, seqno);
				stmt.executeQuery();
				
				sql = "SELECT max(no) FROM attachFile";
				stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				attach_no = rs.getString(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return attach_no;
		
	}

	public void update(Board board, AttachFile attachFile) {
		//보드 update
		String sql = "UPDATE board SET title=?, content=?, open=? WHERE seqno=?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getContent());
			stmt.setString(3, board.getOpen());
			stmt.setString(4, board.getSeqno());
			stmt.executeQuery();
			//첨부파일
			if(attachFile != null) {
				
				String attach_no = insertAttachFile(board.getSeqno(), attachFile);
				String fileType = attachFile.getFileType();
				
				//썸네일
				if(fileType.substring(0, fileType.indexOf("/")).equals("image")) {
					insertThumbNail(attach_no, attachFile);
				}
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

	public int getTotalRec() {
		int total = 0;
		
		String sql = "SELECT count(*) as total FROM board WHERE isdel !='Y' AND open='Y'";
		PreparedStatement stmt;
		
		try {
			stmt= conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			total = rs.getInt("total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
}
