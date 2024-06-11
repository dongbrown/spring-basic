package com.bs.spring.beantest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component // -> POJO(클래스)를 bean으로
//@Primary 
public class BeanTest {
	
	private String title = "어노테이션 빈";

}
