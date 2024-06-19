package com.bs.spring.controller;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final WebApplicationContext context;
	
	@RequestMapping("/")
	public String index(HttpSession session, 
			HttpServletResponse response, Model model) {
		
		//log4j가 제공하는 Logger를 이용해서 로그메세지 출력하기
		// 레벨에 따른 logger -> debug > info > warn > error (포함관계 debug는 info, warn, error 포함)
		// debug()
		// info()
		// warn()
		// error()
		
		logger.debug("debug로그 출력");
		logger.info("info로그 출력");
		logger.warn("warn로그 출력");
		logger.error("error로그 출력");
		//매개변수로 넣은게 메세지로 출력 (로그 + (- 메세지))
		
		session.setAttribute("sessionId", "GDJ79");
		Cookie c = new Cookie("cookieData", "cookiecookie");
		c.setMaxAge(60 * 60 * 24);
		response.addCookie(c);
		
		
		//국제화 문구 처리하기
//		String message = context.getMessage("greeting", null, Locale.US); 
		String message = context.getMessage("greeting", null, Locale.JAPAN); 
//		String message = context.getMessage("greeting", null, Locale.KOREA); 
		model.addAttribute("greeting", message);
		model.addAttribute("msg", context.getMessage("test.msg", new Object[] {"気持ちいい","ドンフン\n"}, Locale.JAPAN));
	
		return "index";
	}

}
