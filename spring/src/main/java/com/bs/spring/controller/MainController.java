package com.bs.spring.controller;

import java.net.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String index(HttpSession session, 
			HttpServletResponse response) {
		session.setAttribute("sessionId", "GDJ79");
		Cookie c = new Cookie("cookieData", "cookiecookie");
		c.setMaxAge(60 * 60 * 24);
		response.addCookie(c);
		return "index";
	}

}
