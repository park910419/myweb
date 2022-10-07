package com.syp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.syp.common.LoginImpl;
import com.syp.dto.Board;
import com.syp.dto.Criteria;
import com.syp.dto.Page;
import com.syp.service.BoardService;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/board/")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	//@GetMapping("list")
	//@PostMapping("list") //get, post 따로하거나 or ↓이렇게
	@RequestMapping(value="list", method= {RequestMethod.POST, RequestMethod.GET}) 
	private String list(Criteria cri, Model model) {
		//게시판 리스트
		if(cri.getCurrentPage() == 0) cri.setCurrentPage(1);
		if(cri.getRowPerPage() == 0) cri.setRowPerPage(5);
		
		List<Board> board = boardService.list(cri);
		
		model.addAttribute("pageMaker", new Page(boardService.getTotalRec(cri), cri));
		model.addAttribute("board", board);
		return "/board/boardList";		
	}
	
	//등록
	@GetMapping("regForm")
	public void regForm() {
	}
	
	@PostMapping("register")
	public String register(Board board, 
						  //@RequestParam("filename") 다른이름으로 하고싶으면 requestparam으로 설정
						  MultipartFile filename, 
						  HttpSession sess,
						  RedirectAttributes rttr) {
		
		System.out.println("보드등록:"+filename);
		
		board.setId(((LoginImpl)sess.getAttribute("loginUser")).getId());
		
		rttr.addFlashAttribute("seqno", boardService.insertBoard(board, filename));
		
		return "redirect:/board/detail";
	}
	
	//디테일
	@GetMapping("detail")
	public String detail(@ModelAttribute("seqno") String seqno, 
						Model model,
						RedirectAttributes rttr) {
		
		model.addAttribute("board", boardService.searchBoard(seqno));
		
		return "/board/detail";
	}
	
	
		/*
		if(cmd.equals("boardList.bo")) {
			//게시판 리스트
			String searchField = req.getParameter("search_field");
			String searchText = req.getParameter("search_text");
			
			String currentPage = req.getParameter("currentPage");
			String rowPerPage = req.getParameter("rowPerPage");
			if(currentPage == null) currentPage = "1";
			if(rowPerPage == null) rowPerPage = "3";
			
			Criteria cri = new Criteria(Integer.parseInt(currentPage), Integer.parseInt(rowPerPage));
			cri.setSearchField(searchField);
			cri.setSearchText(searchText);
			
			List<Board> board = boardService.list(cri);
			
			req.setAttribute("pageMaker", new Page(boardService.getTotalRec(cri), cri));
			req.setAttribute("board", board);
			goView(req, resp, "/board/boardList.jsp");			
		} else if(cmd.equals("boardDetail.bo")) {
			//게시판 세부내용 출력
			String seqno = req.getParameter("seqno");
			if(seqno == null) {
				seqno = (String)req.getAttribute("seqno");
			}
			req.setAttribute("board", boardService.searchBoard(seqno));	
			
			String page = req.getParameter("page");
			
			if(page != null) {
				goView(req, resp, "/board/modify.jsp");
			} else {
				goView(req, resp, "/board/detail.jsp");
			}
		} else if(cmd.equals("boardRegForm.bo")) {
			goView(req, resp, "/board/boardReg.jsp");
		} else if(cmd.equals("boardReg.bo")) {
			req.setAttribute("seqno", boardService.insertBoard(req, resp));
			goView(req, resp, "boardDetail.bo");
		} else if(cmd.equals("modify.bo")) {
			req.setAttribute("seqno", boardService.update(req, resp));
			goView(req, resp, "boardDetail.bo");
		} else if(cmd.equals("boardDelete.bo")){
			boardService.delete(req.getParameter("seqno"));
			goView(req, resp, "boardList.bo");
		}
		
	}
	*/
	
}