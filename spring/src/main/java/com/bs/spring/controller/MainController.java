package com.bs.spring.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping("/")
	public String index(HttpSession session, 
			HttpServletResponse response) {
		
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
		return "index";
	}

}
