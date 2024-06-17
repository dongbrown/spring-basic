package com.bs.spring.common.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.bs.spring.common.exception.BadAuthenticationException;
import com.bs.spring.member.model.dto.Member;

@Component
@Aspect
public class AuthenticationCheckAspect {
	
	//memo insert 권한 admin만 가능하도록
	@Before("execution(* com.bs.spring.memo.model.dao..insert*(..))") //Pointcut 없이 1회용
	public void checkAdmin(JoinPoint jp) {
		
		//session 가져오기
		HttpSession session = (HttpSession) RequestContextHolder.currentRequestAttributes()
		.resolveReference(RequestAttributes.REFERENCE_SESSION);
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null || !loginMember.getUserId().equals("admin")) 
			throw new BadAuthenticationException("권한이 없습니다.");{
			//Excetption 발생시키기! -> RuntimeException (Unchecked Exception)
			// 나만의 Exception 만들기
			
		}
		
	}
	
}
