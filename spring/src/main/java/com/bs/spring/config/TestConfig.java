package com.bs.spring.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import com.bs.spring.beantest.BeanTest;
import com.bs.spring.beantest.Food;
import com.bs.spring.beantest.Person;

@Configuration // 자바코드로 설정 파일 작성 -> springbean configuration.xml(servlet-context.xml)과 동일 -> bean 등록 가능
@ComponentScan(basePackages = "com.bs.spring", 
		includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bs.spring.beantest.test.*")}
		) 
//@ComponentScan : 
// includeFilters : 배열 방식으로 @ComponentScan.Filter 
//  type : 패턴을 해석하는 방식, REGEX - 정규표현식, pattern에 작성한 패키지에 있는 클래스들 Bean 자동 등록
public class TestConfig {

	//bean 등록하기
	//@Bean어노테이션이 선언된 메소드를 선언
	
	@Bean(name = "f")
	//(name="") 속성 부여하지 않으면 메소드 명이 Bean의 id값이 된다!
	//"f" => Bean의 id값
	public Food food() {
		return Food.builder().build();
	}
	
	@Bean(name = "food")
	public Food food2() {
		return Food.builder().name("피자").price(2500).build();
	}
	@Bean(name = "gom")
	@Order(2) //순서 지정
	public Food food3(List<Person> persons) { //아래와 같음
//	public Food food3(@Autowired List<Person> persons) {
		return Food.builder().name("곰탕").likePerson(persons).price(9000).build();
	}
	// List<Person> persons 빈들이 알아서 들어감 
	
//	@Bean(name = "gom2")
//	@Order(1) //순서 지정
//	public Food food4(@Qualifier("p") Person per) { //아래와 같음
//		return Food.builder().name("곰탕").likePerson(List.of(per)).price(9000).build();
//	}
	
	@Bean
	@Primary // 2개 이상일 때 최우선 주입
	public BeanTest listBean() {
		List<Food> foods = List.of(food(), food2());
		return new BeanTest();
	}
	
//	@Bean
//	public Gson gson() {
//		Gson gson = new Gson();
////		gson.newBuilder().~
//		return new Gson();
//	}
	
//	@Bean
//	public BasicDataSource datasource() {
//		//url, 계정 등 등록해놓고 사용
//		BasicDataSource source = new BasicDataSource();
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
