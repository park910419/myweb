package com.syp.service;

import java.io.File;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syp.common.LoginImpl;
import com.syp.dao.BoardDao;
import com.syp.dao.BoardDaoImp;
import com.syp.dto.AttachFile;
import com.syp.dto.Board;
import com.syp.dto.Criteria;

@Service
public class BoardServiceImp implements BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	private static final String CHARSET = "utf-8";

	@Override
	public List<Board> list(Criteria cri) {
		return boardDao.boardList(cri);
	}

	@Override
	public Board searchBoard(String seqno) {
		return boardDao.boardDetail(seqno);
	}

	@Override
	public String insertBoard(HttpServletRequest req, HttpServletResponse resp) {
		
		 DiskFileItemFactory factory = new DiskFileItemFactory();
		 factory.setDefaultCharset(CHARSET);
		 ServletFileUpload upload = new ServletFileUpload(factory);
		 
		 Board board = new Board();
		 AttachFile attachFile = null;
		 FileService fileService = new FileServiceImpl();
		 
		 try {
			List<FileItem> items = upload.parseRequest(req);
			System.out.println("폼에서 넘어온 개수:"+items.size());
			for(FileItem item : items) {
				if(item.isFormField()) {
					 board = getFormParameter(item, board);
				} else {
					attachFile = fileService.fileUpload(item); 
				}
			}
			
		 } catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LoginImpl login = (LoginImpl)req.getSession().getAttribute("loginUser");
		board.setId(login.getId());
		
		return boardDao.insert(board, attachFile);

	}
	

	public String update(HttpServletRequest req, HttpServletResponse resp) {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		 factory.setDefaultCharset(CHARSET);
		 ServletFileUpload upload = new ServletFileUpload(factory);
		 
		 Board board = new Board();
		 AttachFile attachFile = null;
		 FileService fileService = new FileServiceImpl();
		 
		 try {
			List<FileItem> items = upload.parseRequest(req);
			System.out.println("폼에서 넘어온 개수:"+items.size());
			for(FileItem item : items) {
				if(item.isFormField()) {
					 board = getFormParameter(item, board);
				} else {
					attachFile = fileService.fileUpload(item); 
				}
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LoginImpl login = (LoginImpl)req.getSession().getAttribute("loginUser");
		board.setId(login.getId());

		boardDao.update(board, attachFile);
		return board.getSeqno();
	}
	
	Board getFormParameter(FileItem item, Board board) {
		//<input>태그값
		System.out.printf("필드이름: %s, 필드값:%s\n", item.getFieldName(), item.getString());
		if(item.getFieldName().equals("title")) {
			board.setTitle(item.getString());
		}
		if(item.getFieldName().equals("open")) {
			board.setOpen(item.getString());
		}
		if(item.getFieldName().equals("content")) {
			board.setContent(item.getString());
		}
		if(item.getFieldName().equals("seqno")) {
			board.setSeqno(item.getString());
		}
		return board;
	}

	public int getTotalRec(Criteria criteria) {
		return boardDao.getTotalRec(criteria);
	}
	
	@Override
	public void delete(String seqno) {
		Map<String, String> map = boardDao.deleteByNo(seqno);
		
		String savefilename = map.get("savefilename");
		String filepath = map.get("filepath");
		String thumb_filename = map.get("thumb_filename");
		
		if(savefilename != null) {
			//첨부파일삭제
			File file = new File(filepath+savefilename);
			if(file.exists()) {
				file.delete();
			}
			
			//썸네일삭제
			if(thumb_filename != null) {
				File thumb_file = new File(filepath + "thumbnail/" + thumb_filename);
				if(thumb_file.exists()) {
					thumb_file.delete();
				}
			}
		}
	}

	@Override
	public String insertBoard(Board board, MultipartFile files) {
		
		FileService fileService = new FileServiceImpl();
		return boardDao.insert(board, fileService.fileUpload(files));
	
	}

}
