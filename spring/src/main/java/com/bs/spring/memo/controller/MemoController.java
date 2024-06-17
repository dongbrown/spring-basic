package com.bs.spring.memo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bs.spring.common.PageFactory;
import com.bs.spring.memo.model.dto.Memo;
import com.bs.spring.memo.model.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/memo")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class MemoController {
	private final MemoService service;
	private final PageFactory page; //requiredArgsConstructor에 의해 의존성 주입
	
	@GetMapping("/memolist")
	public void memoList(@RequestParam(defaultValue = "1") int cPage,
			@RequestParam(defaultValue = "5") int numPerpage,
			Model m, @ModelAttribute("memo") Memo memo) {
		List<Memo> memos = service.selectMemoList(Map.of("cPage", cPage, "numPerpage", numPerpage));
		int totalData = service.selectMemoCount();
		m.addAttribute("pageBar", page.getPage(cPage, numPerpage, totalData, "memolist"));
		//memolist => 상대경로 => /memo + memolist 
		m.addAttribute("memos", memos);
		System.out.println(memos);
		
	}
	
	@PostMapping("/enrollmemo.do")
	public String insertMemo(@ModelAttribute @Validated Memo memo,
			BindingResult isResult, Model m) {
		if(isResult.hasErrors()) {
			m.addAttribute("memos", service.selectMemoList(Map.of("cPage", 1 ,
					"numPerpage", 5)));
			m.addAttribute("pageBar", page.getPage(1, 5, service.selectMemoCount(), "memolist"));
			return "memo/memolist";
		}
		
		int result = service.insertMemo(memo);
		if(result > 0) {
			return "redirect:/memo/memolist";
		}else {
			m.addAttribute("msg", "메모 저장 실패"); 
			m.addAttribute("loc", "/memo/memolist"); 
			return "common/msg";
		}
	}
	
	@DeleteMapping("/deletememo.do")
	public String deleteMemo(@RequestParam int memoNo) {
	    int result = service.deleteMemo(memoNo);
	    if (result > 0) {
	        log.debug("삭제 완료");
	    } else {
	        log.debug("삭제 실패");
	    }
	    return "redirect:/memo/memolist";
	}




}
