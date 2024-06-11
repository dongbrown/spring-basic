package com.bs.spring.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bs.spring.demo.model.dto.Address;
import com.bs.spring.demo.model.dto.Demo;

@Controller
public class DemoController {
	//Web에서 Controller객체는 client요청을 받아 서비스를 진행
	//Domain => URL 
	//서비스를 한개의 메소드로 처리 -> URL주소와 연결하기 위해서 @RequestMapping을 사용
	//@RequestMapping("URL매핑주소") -> URL주소와 연결
	
//	- @Controller의 메소드를 선언하는 방법
//    - Request주소와 매핑하기 위해서 어노테이션을 선언
//        - @RequestMapping(”주소”) : 요청 method가 GET, POST 모두 매핑할 때
//        - Rest방식으로 서비스를 구현했을 때 사용!
//        - @GetMapping(”주소”) : 요청방식이 GET일 때 → GET방식일때만 실행(조회)
//        - @PostMapping(”주소”) : 요청방식이 POST일 때 → POST방식일때만 실행(저장)
//        - @PutMapping(”주소”) : 요청방식이 PUT일 때 → PUT방식일때만 실행(수정)
//        - @DeleteMapping(”주소”) :  요청방식이 DELETE일 때 → DELETE방식일때만 실행(삭제)
//        
//        → 메소드 방식이 일치하지 않으면 에러를 발생시킴 → 에러페이지로 이동!
//        
//	- 메소드 선언 방식
//    
//    → 반환형 메소드명([매개변수,..])
//    
//    1. 반환형
//        
//        1) String : InternalResourceViewResolver클래스를 이용해서 내부에서 jsp 파일을 선택할 때
//        prefix value + 반환값 + suffix value 파일을 찾음   suffix → .jsp
//        
//        2) void : URL 매핑주소가 view이름과 같을 때 사용, 
//        직접 HttpServletResponse객체를 이용해서 응답해줄 때
//        
//        3) ModelAndView : Model객체와 view를 한번에 저장하는 객체로,
//         jsp에 보낼 데이터와 view이름을 저장해서 반환
//        
//        4) ViewResolver를 구현한 클래스 : 구현된 클래스의 객체를 반환 → 라이브러리 이용할 때(pdf, xml,,)
//        
//        5) 일반 클래스타입 : Data를 json방식으로 응답할 때 → converter를 등록

//→ @ResponseBody 어노테이션을 추가(반환형 앞, 메소드 위) → Rest방식으로 data 응답
//
//2. 매개변수 → Spring이 알아서 준다!
//    
//    1) HttpServeletRequest : 서블릿
//    
//    2) HttpServletResponse : 서블릿
//    
//    3) HttpSession : 서블릿 세션
//    
//    4) java.util.Locale : 서버의 Locale정보를 저장한 객체
//    
//    5) InputStream/Reader : 클라이언트와 연결된 스트림 (받는 것)
//    
//    6) OutputStream / Writer : 클라이언트와 연결된 스트림 (보내는 것)
//    
//    7) 기본자료형 변수 : 클라이언트가 보낸 parameter데이터를 저장할 변수 → 자동 대입해줌
//    
//    - parameter key값과 변수명이 일치
//    - 일치하지 않을 때, @RequestParam어노테이션으로 연결해줌
//    
//    8) DTO(클래스타입) : spring이 만들어서 데이터 저장 후 전달해줌. - command
//    
//    - 필드명과 parameter key값이 붙어야 함.
//    
//    9) [java.util.Map](http://java.util.Map) : parameter를 map방식으로 전달해줌.
//    
//    10) @RequestHeader(”key”) String 변수 선언 : 헤더 정보 가져오기
//    
//    11) @CookieValue(”key”) String 변수 선언 : 쿠키 정보 가져오기
//    
//    12) Model : Spring에서 사용하는 jsp에 데이터를 전달할 때 사용하는 객체(Spring에서 쓰는 공유객체 / 데이터만 저장)
//    
//    ModelAndView(데이터와 뷰 저장) : Model과 view를 한번에 저장하는 객체
//    
//    *매핑메소드에 사용하는 추가 어노테이션
//    
//    @RequestBody : request body의 데이터가 json일 때 → 자바 클래스로 convert
//    
//    @ResponseBody : response body에 클래스를 json으로 convert해서 넘김
//    
//    @ModelAttribute : model 데이터 관리하는 속성 → hibernamte validate 이용 시 사용
//    
//    @SessionAttributes : Model에 저장된 데이터 scope를 Session으로 설정
//    
//    - 메소드명 : 개발자 맘대로. 규칙에 맞춰서 설정
	
	
	@RequestMapping("/demo/demo.do")
	public void demo() {}
//	public String demo() {
//		//화면 전환용 메소드
//		//prefix + 반환값 + suffix
//		// => /WEB-INF/views/demo/demo.jsp
//		return "demo/demo"; // -> RequestDispatcher.forward() 와 동일함
//	}
	
	
	// 서블릿처럼 이용하기
	@RequestMapping("/demo/demo1.do")
	public void demo1(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException{
		System.out.println(request);
		System.out.println(response);
		
		//클라이언트가 보낸 데이터 받아오기
		String devName = request.getParameter("devName");
		int devAge = Integer.parseInt(request.getParameter("devAge"));
		String devGender = request.getParameter("devGender");
		String devEmail = request.getParameter("devEmail");
		String[] devLang = request.getParameterValues("devLang");
		
		Demo d = Demo.builder()
				.devName(devName)
				.devAge(devAge)
				.devGender(devGender)
				.devEmail(devEmail)
				.devLang(devLang)
				.build();
		System.out.println(d);
		request.setAttribute("demo", d); //-> jsp에서 demo.~~ 으로 jstl 사용
		request.getRequestDispatcher("/WEB-INF/views/demo/demoResult.jsp").forward(request, response);
	}
	
	//전달된 파라미터 1:1로 매핑해서 가져오기
	@RequestMapping("/demo/demo2.do")
	public String demo2(String devName, int devAge, String devGender, String devEmail, String[] devLang,  Model m) {
		System.out.println(devName + devAge + devGender + devEmail + devGender + Arrays.toString(devLang));
		
		Demo d = Demo.builder()
				.devName(devName)
				.devAge(devAge)
				.devGender(devGender)
				.devEmail(devEmail)
				.devLang(devLang)
				.build();
		
		//데이터는 Model클래스에 저장함! -> HttpRequest와 같음
		m.addAttribute("demo", d); //-> request.setAttribute와 같음
		
		//int -> 입력x -> null -> 400Error(파싱할 수 없음)
		
		return "demo/demoResult";
	}
	
	//@RequestParam을 이용해서 매개변수 선언하기 (명칭 안 맞을 때 맞추기, 필수값인지 아닌지 설정 가능, 대체값(기본값) 설정 가능)
	// value(파라미터로 넘어온 값의 이름), defaultValue(대체값), required
	@RequestMapping("/demo/demo3.do")
	public String demo3(
			@RequestParam(value="devName") String name, //devName -> name
			@RequestParam(value="devAge", defaultValue="10") int age,
			@RequestParam(value="devEmail") String email,
			@RequestParam(value="devGender", required=false) String gender,
			@RequestParam(value="devLang", required=true) String[] devLang, // required=true -> 반드시 넘겨야 하는 값
			Model m) {
		Demo d = Demo.builder()
				.devName(name)
				.devAge(age)
				.devGender(gender)
				.devEmail(email)
				.devLang(devLang)
				.build();
		m.addAttribute("demo", d);
		return "demo/demoResult"; 
//		   <!-- 기본 viewresolver구현체 등록했기 떄문에 앞 뒤 붙음 -->
//		   <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//		      <beans:property name="prefix" value="/WEB-INF/views/"/>
//		      <beans:property name="suffix" value=".jsp"/>
//		   </beans:bean>
	}
	
	//DTO를 파라미터로 선언하기
	@RequestMapping("/demo/demo4.do")
	public String demo4(@ModelAttribute Demo demo, Address address) {
//			, Model m) {
		demo.setAddress(address);
//		m.addAttribute("demo", demo);
		System.out.println(demo);
		return "demo/demoResult";
	}
	//@ModelAttribute 사용 -> 매개변수에 Model m + m.addAttribute("demo", demo); 한 것과 같음
	
	//Map으로 클라이언트가 보낸 데이터 받기
	@RequestMapping("/demo/demo5.do")
	public String demo5(@RequestParam Map param, String[] devLang, Model m) { // Map으로 받으면 배열은 첫번째 것만 들어감! -> 따로 받아야 함
		System.out.println(param);
		param.put("devLang", devLang);
		m.addAttribute("demo", param);
		return "demo/demoResult";
	}
	
	//추가 데이터 전달받아 처리하기
	//RequestHeader의 값, Cookie값, Session에 저장된 값
	
	
	
	
	
	
	
}
