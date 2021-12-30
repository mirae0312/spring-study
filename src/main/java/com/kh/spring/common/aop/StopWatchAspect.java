package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StopWatchAspect {

	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		// joinPoint 수행 전
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		// joinPoint 수행
		Object retObj = joinPoint.proceed();
		
		// joinPoint 수행 후
		stopWatch.stop();
		long duration = stopWatch.getTotalTimeMillis();
		
		Signature sign = joinPoint.getSignature();
		String methodName = sign.getName();
		
		log.debug("{} 소요시간 {}ms", methodName, duration);
		
		return retObj;
	}
}
