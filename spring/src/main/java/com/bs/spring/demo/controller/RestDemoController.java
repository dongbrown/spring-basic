package com.bs.spring.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.spring.demo.model.dto.Demo;
import com.bs.spring.demo.model.service.DemoService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/demos")
public class RestDemoController {
	private final DemoService service;
	
	@GetMapping()
	public @ResponseBody List<Demo> demoAll(
			@RequestParam(defaultValue = "1") int cPage,
			@RequestParam(defaultValue = "5") int numPerpage){
		return service.selectDemoList(
				Map.of("cPage", cPage, "numPerpage", numPerpage));
	}
	
	@GetMapping("/{no}")
	public @ResponseBody Demo demoOne(@PathVariable int no) {
		//@PathVariable -> 경로에 있는 값을 가져와라
		return service.selectDemoByNo(no);
	}
	 
	@PostMapping
	public @ResponseBody Demo insertDemo(@RequestBody Demo demo) {
		int result = service.insertDemo(demo);
		return result > 0 ? demo : null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
