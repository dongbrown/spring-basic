package com.bs.spring.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bs.spring.board.model.dto.Board;
import com.bs.spring.board.model.service.BoardService;
import com.bs.spring.demo.model.dto.Demo;
import com.bs.spring.demo.model.service.DemoService;
import com.bs.spring.member.model.dto.Member;
import com.bs.spring.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Controller
@RestController //@Controller + @ResponseBody -> 객체를 반환(converter 이용해서 json 방식으로 응답!) -> @ResponseBody 안 써도됨!
@RequiredArgsConstructor
@RequestMapping("/demos")
public class RestDemoController {
	
	private final DemoService service;
	private final BoardService boardService;
	private final MemberService memberService;
	
	
	//ResponseEntity클래스를 이용해서 응답하기
	// -> (헤더에 대한 정보 httpstatus, body에 대한 정보를 저장할 수 있음, 응답상태도 원하는대로 설정 가능)
	
	@GetMapping()
//	public @ResponseBody List<Demo> demoAll(
	public ResponseEntity<List<Demo>> demoAll(
			@RequestParam(defaultValue = "1") int cPage,
			@RequestParam(defaultValue = "5") int numPerpage){
		ResponseEntity<List<Demo>> response;
		List<Demo> result = service.selectDemoList(
				Map.of("cPage", cPage, "numPerpage", numPerpage));
		
		if(result.size() == 0) {
			return ResponseEntity.ok(result);
		}else {
			return ResponseEntity.notFound().build();
		}
		
//		return service.selectDemoList(
//				Map.of("cPage", cPage, "numPerpage", numPerpage));
	}
	
	@GetMapping("/{no}")
//	public @ResponseBody Demo demoOne(@PathVariable int no) {
	public Demo demoOne(@PathVariable int no) {
		//@PathVariable -> 경로에 있는 값을 가져와라
		return service.selectDemoByNo(no);
	}
	 
	@PostMapping
//	public @ResponseBody Demo insertDemo(@RequestBody Demo demo) {
	public ResponseEntity<Demo> insertDemo(@RequestBody Demo demo) {
		int result = service.insertDemo(demo);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("myheader", "mydata"); //헤더 저장
		
		return new ResponseEntity<>(result > 0 ? demo : null, headers, HttpStatus.CREATED);
//		return result > 0 ? demo : null;
	}
	
	//@PathVariable 이용한 URI 템플릿 설정 방법
	//@PathVariable 어노테이션 값으로 {템플릿 변수} 사용
	//{템플릿 변수} 와 동일한 이름을 갖는 파라미터를 @PathVariable 어노테이션을 이용한 요청처리 메소드에 추가
	@GetMapping("/member/{id}")
	public ResponseEntity<Member> getMember(@PathVariable String id) {
		return ResponseEntity.ok(memberService.selectMemberById(id));
	}
	
	@GetMapping("/boards")
	public ResponseEntity<List<Board>> getBoards(
			@RequestParam (defaultValue = "1") int cPage,
			@RequestParam (defaultValue = "5") int numPerpage)
			{
		return ResponseEntity.ok(boardService.selectBoardList(Map.of("cPage", cPage, "numPerpage", numPerpage)));
	}
	
	@GetMapping("/dockers")
	public ResponseEntity<String> restTemplate(){
		final String url = "http//14.36.141.71:9994/dockers/";
		RestTemplate request = new RestTemplate();
		ResponseEntity<String> result = 
				request.getForEntity(url, String.class);
		String data = request.getForObject(url, String.class);
		log.debug(data);
		return result;
	}
	
	@GetMapping(value = "/log/{name}",
			consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE) 
	//consumes : 받을 content type 설정 -> 다르면 받지 않음
	//produces : Header - Accept type 설정
	public ResponseEntity<String> restTemplateParam(@PathVariable String name){
		final String url = "http://14.36.141.71:9994/log/{name}";
		RestTemplate request = new RestTemplate();
		Map<String, String> param = new HashMap<>();
		param.put("name", name);
		return request.getForEntity(url, String.class, param);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
