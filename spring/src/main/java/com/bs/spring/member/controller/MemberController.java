package com.bs.spring.member.controller;

import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bs.spring.member.model.dto.Member;
import com.bs.spring.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j //-> 롬복이 제공하는 로그 (log.debug("{}", m);
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"}) //클래스 선언부에 위치, "loginMember"는 session에 저장
public class MemberController {
	
	private final MemberService service;
	private final BCryptPasswordEncoder pwencoder;
	
	
	@PostMapping("/login.do")
	public String login(String userId, String pw, Model model) { 
//			HttpSession session, Model model) {
		System.out.println(userId + pw);
		Member m = service.selectMemberById(userId);
//		System.out.println(m);
		log.debug("{}", m);
		
		String page = "";

//		if(m != null && m.getPassword().equals(pw)) {
		if(m != null && pwencoder.matches(pw, m.getPassword())) {
		//pwencoder.matches(pw, m.getPassword()) => 암호화 된 비밀번호와 비교
//			session.setAttribute("loginMember", m);
			model.addAttribute("loginMember", m);
			page = "redirect:/"; // 성공 -> 메인으로 redirect
		}else {
			model.addAttribute("msg", "아이디나 패스워드가 일치하지 않습니다.");
			model.addAttribute("loc", "/");
			page = "common/msg"; // 실패 메세지
		}
		return page; 
	}
	//Model -> 요청이 끝나면 사라짐(request와 같이)
	
	@GetMapping("/logout.do")
//	public String logout(HttpSession session) {
	public String logout(SessionStatus session) {
//		session.invalidate();
		if(!session.isComplete()) session.setComplete(); //session.invalidate()와 같음
		return "redirect:/";
	}
	@GetMapping("/enrollmember.do")
	public String enrollMember(@ModelAttribute("member") Member m, Model model) {
		//@ModelAttribute("member") Member m => model.addAttribute("member", m) 과 같음 
		return "member/enrollmember";
	}
	
	@PostMapping("/enrollmemberend.do")
	// 매개변수 -> @Validated 먼저 그 다음 BindingResult
	public String enrollMemberEnd(@Validated Member m, BindingResult isResult, Model model) {
		if(isResult.hasErrors()) {
			//설정한 유효성 검사를 통과하지 못한 것!
			return "member/enrollmember";
		}
		//패스워드 암호화
		String encodePw = pwencoder.encode(m.getPassword());
		System.out.println(encodePw);
		log.debug(encodePw);
		m.setPassword(encodePw);
		
		int result = service.insertMember(m);
		model.addAttribute("msg", "");
		model.addAttribute("loc", "");
		if(result > 0) {
			System.out.println("회원가입 성공");
			return login(m.getUserId(), m.getPassword(), model); //가입과 동시에 로그인 처리
//			return "redirect:/";	
		}else {
			System.out.println("회원가입 실패");
			model.addAttribute("msg", "실패");
			model.addAttribute("loc", "/member/enrollmember.do");
			return "common/msg";
		}
	}
	//hibernate - validator 객체 => 유효성 검사
	
	
	
	
	

}
