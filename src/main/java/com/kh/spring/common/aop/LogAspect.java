package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LogAspect {

	@Pointcut("execution(* com.kh.spring.memo..*(..))")
	public void pointcut() {}
	
	@Around("pointcut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature(); // 시그니처메소드로 메소드정보 가져옴
		String type = signature.getDeclaringTypeName(); // 클래스명
		String methodName = signature.getName(); // 메소드명
		
		// joinPoint 호출 전
		log.debug("[Before] {}.{}", type, methodName);

		// 주 업무로직의 특정메소드
		Object retObj = joinPoint.proceed(); // 호출
		
		// joinPoint 호출 후
		log.debug("[After] {}.{}", type, methodName);
		
		return retObj;
	}
}
