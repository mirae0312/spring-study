package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogAspect {

	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature(); // 시그니처메소드로 메소드정보 가져옴
		String type = signature.getDeclaringTypeName(); // 클래스명
		String methodName = signature.getName(); // 메소드명
//		StopWatch stopWatch = new StopWatch();
		
		// joinPoint 호출 전
		log.debug("[Before] {}.{}", type, methodName);
//		stopWatch.start();
		
		// 주 업무로직의 특정메소드
		Object retObj = joinPoint.proceed(); // 호출
//		stopWatch.stop();
//		long totalTimeMillis = stopWatch.getTotalTimeMillis();
		
		// joinPoint 호출 후
		log.debug("[After] {}.{}", type, methodName);
		
//		log.debug("메소드 : {}, 실행시간 : {}ms", methodName, totalTimeMillis);
		
		return retObj;
	}
}
