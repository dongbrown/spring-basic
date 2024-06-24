package com.bs.spring.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SecurityController {

	//인증 성공 후 연결될 서비스
	@RequestMapping("/loginsuccess")
	public String loginsuccess() {
		log.debug("로그인 성공!");
		//로그인한 정보를 가져오기
		Object o = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		//인증받은 객체
		log.debug("{}", o);
		//User [Username=admin, Password=[PROTECTED], Enabled=true, 
		// AccountNonExpired=true, credentialsNonExpired=true,
		// AccountNonLocked=true, Granted Authorities=[admin]]
		
		return "redirect:/";
	}
	
	@RequestMapping("/loginfail")
	public String loginFail(Model m) {
		m.addAttribute("msg", "로그인 실패!");
		m.addAttribute("loc", "/loginpage");
		return "common/msg";
	}
	
	
	
	
	
	
	
	
	
	
}
