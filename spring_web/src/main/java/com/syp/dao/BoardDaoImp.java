package com.syp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.syp.dto.AttachFile;
import com.syp.dto.Board;
import com.syp.dto.Criteria;
import com.syp.dto.Reply;
import com.syp.dto.Thumbnail;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class BoardDaoImp implements BoardDao {
	
	@Autowired
	private DataSource ds;
	
	//private final Connection conn = OracleConn.getInstance().getConn();
	
	public List<Board> boardList(Criteria cri) {
		
		Connection conn = null;
		CallableStatement stmt = null;
		List<Board> board = new ArrayList<Board>();
		
		String search_title = null;
		String search_name = null;
		
		//제목검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("title")) {
			search_title = cri.getSearchText();
		}
		//이름검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("name")) {
			search_name = cri.getSearchText();
		}
		
		String sql = "call p_getboardlist(?, ?, ?, ? ,?)";
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setString(3, search_name);
			stmt.setString(4, search_title);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(5);
			
			while(rs.next()){
				Board b = new Board();
				
				b.setNo(rs.getString("rn"));
				b.setTitle(rs.getString("title"));
				b.setWdate(rs.getString("wdate"));
				b.setName(rs.getString("name"));
				b.setCount(rs.getString("count"));
				b.setSeqno(rs.getString("seqno"));
				board.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		//	resourceClose(conn, stmt);	
		}
		
		return board;
	}

	public Board boardDetail(String seqno) {
		
		Connection conn = null;
		CallableStatement stmt = null;
		Board board = new Board(); 
		
		try {
			//조회수 카운트
			String sql = "call p_board_detail(?,?,?,?)";
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, Integer.parseInt(seqno));
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.executeUpdate();
			
			//게시글
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			rs.next();
			board.setSeqno(seqno);
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			board.setId(rs.getString("id"));
			board.setWdate(rs.getString("wdate"));
			board.setCount(rs.getString("count"));
			board.setName(rs.getString("name"));	
			board.setOpen(rs.getString("open"));
						
			//댓글
			List<Reply> reply = new ArrayList<Reply>();
			
			rs = (ResultSet)stmt.getObject(3);
			
			while(rs.next()){ 
				Reply r = new Reply();
				r.setComment(rs.getString("content")); 
				r.setId(rs.getString("id"));
				r.setWdate(rs.getString("wdate")); 
				reply.add(r);
				} 
				
				board.setReply(reply);

			//첨부파일	
			List<AttachFile> fileList = new ArrayList<AttachFile>();
			
			rs = (ResultSet)stmt.getObject(4);
			
			while(rs.next()) {
				AttachFile attachfile = new AttachFile();
				attachfile.setNo(rs.getString("no"));
				attachfile.setFileName(rs.getString("filename"));
				attachfile.setSaveFileName(rs.getString("savefilename"));
				attachfile.setFileSize(rs.getString("filesize"));
				attachfile.setFileType(rs.getString("filetype"));
				attachfile.setFilePath(rs.getString("filepath"));
				
				Thumbnail th = new Thumbnail();
				th.setFileName(rs.getString("thumb_name"));
				th.setFileSize(rs.getString("thumb_size"));
				th.setFilePath(rs.getString("thumb_path"));
				attachfile.setThumbnail(th);

				fileList.add(attachfile);
			}	
				
			board.setAttachFile(fileList);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resourceClose(conn, stmt);	
		}
		
		return board;
	}
	

	public String insert(Board board, AttachFile attach) {
		
		Connection conn = null;
		CallableStatement stmt = null;
		String seqno = null;
		
		try {
			String sql = "call p_insert_board(?,?,?)";
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_board = StructDescriptor.createDescriptor("OBJ_BOARD", conn);
			Object[] obj_board = {board.getTitle(), board.getContent(), board.getOpen(), board.getId()};
			STRUCT board_rec = new STRUCT(st_board, conn, obj_board);

			stmt.setObject(1, board_rec);

			//첨부파일
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("ATTACH_NT", conn);
			ARRAY attach_arr = null;
			
			//첨부파일
			if(attach != null) {
				
				StructDescriptor st_thumb = StructDescriptor.createDescriptor("OBJ_ATTACH_THUMB", conn);
				STRUCT attach_thumb_rec = null;
				Object[] obj_thumb = null;
				
				if(attach.getThumbnail() != null) {
					
					obj_thumb = new Object[] { attach.getThumbnail().getFileName(),
										       attach.getThumbnail().getFileSize(),
										       attach.getThumbnail().getFilePath()};

				} 
				
				attach_thumb_rec = new STRUCT(st_thumb, conn, obj_thumb);
				
				StructDescriptor st_attach = StructDescriptor.createDescriptor("OBJ_ATTACH", conn);
				
				Object[] obj_attach = {attach.getSaveFileName(), attach.getFileName(), attach.getFileSize(), 
									   attach.getFileType(), attach.getFilePath(), 
									   attach_thumb_rec};
				
				STRUCT[] attach_rec = new STRUCT[1];
				attach_rec[0] = new STRUCT(st_attach, conn, obj_attach);
				
				attach_arr = new ARRAY(desc, conn, attach_rec);
				
			} else {
				attach_arr = new ARRAY(desc, conn, null);
			}
			
			stmt.setArray(2, attach_arr);
			
			stmt.registerOutParameter(3, OracleType.VARCHAR2);
			stmt.executeQuery();
			
			seqno = stmt.getString(3);
				
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			resourceClose(conn, stmt);	
		}
		
		return seqno;
	}
	
	public void insertThumbNail(String attach_no, AttachFile attachFile) {
		//썸네일 저장
		
		PreparedStatement stmt = null;
		Connection conn = null;
		
		String sql = "INSERT INTO attachfile_thumb(no, filename, filesize, filepath, attach_no)"
					+ "VALUES(ATTACHFILE_THUMB_SEQ.NEXTVAL, ?, ?, ?, ?)";
		
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement(sql);
				Thumbnail thumb = attachFile.getThumbnail();
				stmt.setString(1, thumb.getFileName());
				stmt.setString(2, thumb.getFileSize());
				stmt.setString(3, thumb.getFilePath());
				stmt.setString(4, attach_no);
				stmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resourceClose(conn, stmt);	
			}
			
	}
	
	public String insertAttachFile(String seqno, AttachFile attachFile) {
		String sql = "INSERT INTO attachFile(no, filename, savefilename, filesize, filetype, filepath, board_seqno)"
				   + "VALUES (ATTACHFILE_SEQ.NEXTVAL, ?,?,?,?,?,?)";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String attach_no = null;
		
			try {
				conn = ds.getConnection();
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, attachFile.getFileName());
				stmt.setString(2, attachFile.getSaveFileName());
				stmt.setString(3, attachFile.getFileSize());
				stmt.setString(4, attachFile.getFileType());
				stmt.setString(5, attachFile.getFilePath());
				stmt.setString(6, seqno);
				stmt.executeQuery();
				
				sql = "SELECT max(no) FROM attachFile";
				conn = ds.getConnection();
				ResultSet rs = stmt.executeQuery();
				rs.next();
				attach_no = rs.getString(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resourceClose(conn, stmt);	
			}
			
			return attach_no;
		
	}

	public void update(Board board, AttachFile attachFile) {
		//보드 update
		String sql = "call p_updateBoard(?, ?, ?, ?)";
		Connection conn = null;
		CallableStatement stmt = null;
		
			try {
				conn = ds.getConnection();
				stmt = conn.prepareCall(sql);
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
			} finally {
				resourceClose(conn, stmt);	
			}

	}

	public int getTotalRec(Criteria cri) {
		
		Connection conn = null;
		CallableStatement stmt = null;
		
		int total = 0;
		String search_title = null;
		String search_name = null;
		//제목검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("title")) {
			search_title = cri.getSearchText();
		}
		
		//이름검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("name")) {
			search_name = cri.getSearchText();
		}
		
		String sql = "call p_getboardtotal(?, ?, ?)";
		
			try {
				conn = ds.getConnection();
				stmt = conn.prepareCall(sql);
				stmt.setString(1, search_name);
				stmt.setString(2, search_title);
				stmt.registerOutParameter(3, OracleTypes.INTEGER);
				stmt.executeQuery();
	
				total = stmt.getInt(3);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resourceClose(conn, stmt);	
			}
		
		return total;
	}

	public Map<String, String> deleteByNo(String seqno) {
		Map<String, String> map = new HashMap<String, String>();
		
		Connection conn = null;
		CallableStatement stmt = null;
		
		String sql = "call p_deleteBoard(?, ?, ?, ?)";
		
			try {
				ds.getConnection();
				stmt = conn.prepareCall(sql);
				stmt.setString(1, seqno);
				stmt.registerOutParameter(2, OracleTypes.VARCHAR);
				stmt.registerOutParameter(3, OracleTypes.VARCHAR);
				stmt.registerOutParameter(4, OracleTypes.VARCHAR);
				stmt.executeQuery();
				System.out.println("프로시저 리턴결과:" + stmt.getString(2));
				map.put("savefilename", stmt.getString(2));
				map.put("filepath", stmt.getString(3));
				map.put("thumb_filename", stmt.getString(4));
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resourceClose(conn, stmt);	
			}
		
		return map;
	}
	
	//자원반납
	private void resourceClose(Connection conn, PreparedStatement stmt) {
		try {
			
			if(stmt != null || conn !=null) {
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	//자원반납
	private void resourceClose(Connection conn, CallableStatement stmt) {
		try {
			
			if(stmt != null || conn != null) {
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public String insertBoard(Board board) {
		
		CallableStatement stmt = null;
		Connection conn = null;
		String seqno = null;
		
		try {
			String sql = "call p_insert_board(?,?)";
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_board = StructDescriptor.createDescriptor("OBJ_BOARD", conn);
			Object[] obj_board = {board.getTitle(), board.getContent(), board.getOpen(), board.getId()};
			STRUCT board_rec = new STRUCT(st_board, conn, obj_board);

			stmt.setObject(1, board_rec);
			stmt.registerOutParameter(2, OracleType.VARCHAR2);
			stmt.executeQuery();
			
			seqno = stmt.getString(2);
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourceClose(conn, stmt);	
		}
		
		return seqno;
	}
	
}
