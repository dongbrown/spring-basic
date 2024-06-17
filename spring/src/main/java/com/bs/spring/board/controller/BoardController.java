package com.bs.spring.board.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bs.spring.board.model.dto.Board;
import com.bs.spring.board.model.service.BoardService;
import com.bs.spring.common.PageFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class BoardController {
	
	private final BoardService service;
	private final PageFactory pageFactory;
	
	@GetMapping("/boardlist.do")
	public void boardList(@RequestParam(defaultValue = "1") int cPage,
			@RequestParam(defaultValue = "5") int numPerpage,
			Model m){
		List<Board> result = service.selectBoardList(Map.of("cPage", cPage, "numPerpage", numPerpage));
		m.addAttribute("boards", result);
		int totalData = service.selectBoardCount();
		System.out.println(result);
		String pageBar = pageFactory.getPage(cPage, numPerpage, totalData, "boardlist.do");
		m.addAttribute("pageBar", pageBar);
	}
	

	
	@PostMapping("/insertboard.do")
	public String insertBoard(Board b, Model model) {
		int result = service.insertBoard(b);
		String msg, loc;
		if(result > 0) {
			msg = "게시글 등록 성공!";
			loc = "/board/boardlist.do";
		}else {
			msg = "게시글 등록실패 다시 시도하세요!";
			loc = "/board/inputboard.do";
		}
		return "common/msg";
	}
	
	
//	@GetMapping("/inputboard.do")
//	public void inputboard() {
//	}
	//단순 페이지 이동 (화면 전환)
	
	
	
	 @GetMapping("/boardDetail")
	 public String boardDetail(int boardNo, Model m) {
		 service.updateBoardReadCount(boardNo);
		 return "board/boardDetail";
	 }
	
	
	@DeleteMapping("/deleteBoard.do")
	public String deleteBoard(@RequestParam int boardNo) {
		int result = service.deleteBoard(boardNo);
		if(result > 0) {
			log.debug("삭제 완료");
		}else {
			log.debug("삭제 실패");
		}
		return "redirect:/board/boardlist.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
