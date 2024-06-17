package com.bs.spring.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.bs.spring.common.exception.BadAuthenticationException;

@Configuration //xml과 같은 기능
@EnableWebMvc // 페이지 이동(화면 전환)
public class WebMVCConfig implements WebMvcConfigurer{

	//페이지 이동(화면 전환)
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/board/inputboard.do") // 이 요청이 들어오면
		.setViewName("board/inputBoard"); // 이 페이지로 이동
	}

	//예외처리할 bean을 등록해서 활용할 수 있음
	// HandlerExceptionResolver를 구현한 구현체
	@Bean
	public HandlerExceptionResolver handleException() {
		SimpleMappingExceptionResolver smer = 
				new SimpleMappingExceptionResolver();
		
		Properties mappingException = new Properties();// Properties -> key-value 형식
		mappingException.setProperty(
				BadAuthenticationException.class.getName()
				, "common/error/authentication"); // url-> prefix, suffix 붙어서 넘어감
		smer.setExceptionMappings(mappingException); //Proipeties => 에러 페이지 매핑

		smer.setDefaultErrorView("common/error/error"); //default 에러페이지 설정
		
		return smer;
	}
}
