package com.bs.spring.ajax.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.spring.demo.model.dto.Demo;
import com.bs.spring.demo.model.service.DemoService;
import com.bs.spring.member.model.dto.Member;
import com.bs.spring.member.model.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ajax")
@RequiredArgsConstructor
public class AjaxTestController {
	
	private final ObjectMapper mapper;
	private final DemoService demoService;
	private final MemberService memberService;
	
	// 1. 기본 서블릿에서 응답했던 방식
//	@GetMapping("/basicajax")
//	public String basicAjax(Model model) {
//		model.addAttribute("lunch", List.of("국밥", "냉면", "쌈밥", "돈까스", "콩국수"));
//		return "ajaxpage/basicAjax";
//	}
	
	
//	public void basicAjax(HttpServletRequest request,
//			HttpServletResponse response, Model model) 
//					throws ServletException, IOException{
//		model.addAttribute("lunch", List.of("국밥", "냉면", "쌈밥", "돈까스", "콩국수"));
//		
//		request.getRequestDispatcher("/WEB-INF/views/ajaxpage/basicAjax.jsp")
//			.forward(request, response);
////		response.getWriter().write("ajaxData");
//	}
	
	@GetMapping("/basicajax")
	public void basicAjax(HttpServletResponse response, Model model) 
		throws ServletException, IOException{
		List<String> lunch = List.of("국밥", "냉면", "쌈밥", "돈까스", "콩국수");
		response.setContentType("application/json;charset=utf-8");
//		gson.toJson(lunch, response.getWriter());
		mapper.writeValue(response.getWriter(), lunch);
	}
	
	//@ResponseBody를 이용해서 객체 자체를 등록된 converter를 이용해서 json으로 응답하기
	// return 객체 //spring이 알아서 converter
	// jackson 이용하기 -> 알아서 파싱해줌
	// servlet-context.xml에 bean 등록
	
	@GetMapping("/lunches")
	@ResponseBody
	public List<String> lunchList(){
		return List.of("국밥", "냉면", "쌈밥", "돈까스", "콩국수");
	}
	
//	@ResponseBody 여기 작성하거나 반환형 앞에 쓰기
	@GetMapping("/demos")
	public @ResponseBody List<Demo> getDemos(){
		return demoService.selectDemoList(Map.of("cPage", 1, "numPerpage", 5));
	}
	
	@ResponseBody
	@GetMapping("/member")
	public Member searchById(String userId) {
		return memberService.selectMemberById(userId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
