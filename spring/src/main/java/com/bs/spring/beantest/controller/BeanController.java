package com.bs.spring.beantest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.spring.beantest.NoBean;
import com.bs.spring.beantest.service.BeanService;
import com.bs.spring.beantest.test.Test;
import com.bs.spring.beantest.test.TestTwo;

@Controller
//@RequiredArgsConstructor // final로 선언된 필드에 대해서만 매개변수 있는 생성자 생성 -> 이 방식 권장
//@AllArgsConstructor
public class BeanController {
	
//	@Autowired
	private final BeanService service; 
	//BeanService에 의존 (의존성 주입) 
	private final NoBean bean;
	
	@Autowired
	private Test test;
	
	@Autowired
	private TestTwo test2;
	
	
	public BeanController(@Autowired BeanService service, @Autowired(required = false) NoBean bean) { // 생략하고 -> @RequiredArgsConstructor
		super();
		this.service = service;
		this.bean = bean;
	}
	
	
	private String name;
	
	//매개변수 있는 생성자로 의존성 주입하는 것 권장 (@AllArgsConstructor 만 작성! 기본 생성자 있으면 안됨)
//	public BeanController(BeanService service) {
//		this.service = service;
//	}
	
	@RequestMapping("/test")
	public String beanTest() {
		System.out.println("BeanController 실행");
		service.testService();
		return "index";
	}
}
