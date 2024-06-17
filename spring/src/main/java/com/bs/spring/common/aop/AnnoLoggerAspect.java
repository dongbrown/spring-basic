package com.bs.spring.common.aop;


import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 롬복 없으면 로그팩토리 객체 만들어서 써야함!
@Component //-> 컴포넌트 등록 -> Spring이 관리
@Aspect
public class AnnoLoggerAspect {
	//ex) 누가 DB 수정/삭제했는지 전/후 로그 DB에 저장 혹은 파일에 저장
	
	//@Pointcut(타겟메소드에 대한 표현식)
	//메소드 선언
	
	@Pointcut("execution(* com.bs.spring.memo..*(..))") //타겟메소드가 실행될 때
	public void test() {}
	

	
	@Before("test()") //매개변수에 참조할 Pointcut 메소드 대입 
	public void loggerTest(JoinPoint jp) {
		log.debug("----- anno aspect before 실행 -----");
	}
	
	@After("test()")
	public void loggerTestAfter(JoinPoint jp) {
		Signature sig = jp.getSignature();
		log.debug("끝냄 : " + sig.getDeclaringTypeName() + "." + sig.getDeclaringType());
		Object[] params = jp.getArgs();
		if(params != null) {
			for(Object o : params) {
				log.debug("{}", o);
			}
		}
	}
	// -> AOP를 사용하여 메서드 종료 시점에 메서드 정보와 파라미터 정보를 로깅
	
	
//	@Pointcut("execution(* com.bs.spring..insert*(..))") // -> insert로 시작하는 모든 메소드가 실행될 때
//	public void test2() {}
	
	@Before("execution(* com.bs.spring..insert*(..))") // -> @Pointcut 쓰지 않고 작성 => 1회용
	public void insertLogger(JoinPoint jp) {
		log.debug("---------- insert메소드 실행 로그 ----------");
		Signature sig = jp.getSignature();
		log.debug(sig.getDeclaringTypeName() + "." + sig.getName() + "를 실행");
		Object[] param = jp.getArgs();
		if(param != null) {
			log.debug("전달된 파라미터");
			for(Object o : param) {
				log.debug("{}", o);
			}
		}
	}
	
	// within 적용하기 -> 
	@Pointcut("within(com.bs.spring.demo.controller.DemoController)")
	public void test3() {}
	
	@Before("test3()")
	public void withinTest(JoinPoint jp) {
		log.debug("----- within 실행 -----");
	}
	
	//Around 적용하기 => before, after를 동시에 적용
	@Around("within(com.bs.spring..dao.*)")
	public Object daoTest(ProceedingJoinPoint jp) throws Throwable{
		//전, 후 로직을 한번에 설정할 수 있음
		// 지역변수를 전, 후에 공유할 수 있음 (잠깐 쓸 데이터?)
		
		//전에 대한 로직, 후에 대한 로직은 ProceedingJoinPoint클래스에서 제공하는
		//proceed() 메소드를 호출한 라인을 기준으로 나눔 
		//	proceed() 메소드를 호출 전 라인 : before 로직
		//	proceed() 메소드를 호출 후 라인 : after 로직
		//proceed()메소드를 호출하면 Object를 반환 -> 이것 return
		
		log.debug("===== around before log =====");
		StopWatch watch = new StopWatch();
		watch.start();
		Object obj = jp.proceed();
		
		log.debug("===== around after log =====");
		watch.stop();
		log.debug("실행시간 : " + watch.getTotalTimeMillis() + "ms");
		return obj;
	}
	
	@AfterThrowing(value =  "within(com.bs.spring..controller.*)", 
			throwing = "e")
	public void afterThrowingLogger(JoinPoint jp, Throwable e) {
		log.debug("===== exception발생 비상비상! =====");
		Signature sig = jp.getSignature();
		log.error("실행 메소드 : " + sig.getDeclaringTypeName() 
		+ "." + sig.getName());
		log.error("파라미터");
		Object[] param = jp.getArgs();
		if(param != null) {
			Arrays.stream(param).forEach(p -> {
				log.error("{}", p);
			});
		}
		log.error(e.getMessage());
		StackTraceElement[] stackTrace = e.getStackTrace();
		for(StackTraceElement s : stackTrace) {
			log.error("{}", s);
		}
	}
	
	//인터페이스 구현체들한테 적용하기
	@Before("within(com.bs.spring..*Dao+)")
	public void interTest() {
		log.debug("---- dao 인터페이스구현체 적용 ---");
	}
	//생성한 어노테이션이 설정한 메소드에 적용하기
	@Before("@annotation(com.bs.spring.common.aop.MyAnnotation)")
	public void annotest() {
		log.debug("---- 어노테이션적용 메소드 ----");
	}
	
	
	
	
 
	
	
	
	
	
	
	
	
	
	
	
}
