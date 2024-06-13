package com.bs.spring.controller;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class RestSample {

//	@GetMapping
//	public List<Member> getMember() {
//		return members;
//	}
	
	
//	@GetMapping("/{id}")
//	public Member getMemberId(@PathVariable id) {
//		return member;
//	}
	
	@PostMapping
	public boolean insertMember(Member m) {
		return true;
	}
}
