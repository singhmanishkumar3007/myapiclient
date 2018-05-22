package com.tech.boot.myclient.aspects;

import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import com.tech.boot.myclient.constants.LogConstants;

@Configuration
@Aspect
public class MyAspect {

	private static Logger LOGGER = LoggerFactory.getLogger(MyAspect.class);

	@Before("execution(* com.tech.boot.myclient.restclients.*.*(..))") // pointcut
	public void printWelcomeMessage(JoinPoint joinPoint) // this is advice
	{
		LOGGER.info("today's date is {}", Calendar.getInstance().getTime());
		LOGGER.info("Hi Rajesh");
	}

	@After("execution(* com.tech.boot.myclient.restclients.*.*(..))") // pointcut
	public void printByeMessage(JoinPoint joinPoint) // this is advice
	{
		LOGGER.info("Bye Rajesh");
	}

	@Around("@annotation(com.tech.boot.myclient.annotations.MyAnnotation)")
	public Object logAspect(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.info("Entered Method {} with parameters {} ", joinPoint.getSignature().getName(), Arrays
				.asList(joinPoint.getArgs()).stream().map(arg -> arg.toString()).collect(Collectors.joining(",")));

		LOGGER.info("client ip from aspect is {}", MDC.get(LogConstants.API_CLIENT_IP));

		Object obj = joinPoint.proceed();

		LOGGER.info("Exit Method {} with parameters {} ", joinPoint.getSignature().getName(), Arrays
				.asList(joinPoint.getArgs()).stream().map(arg -> arg.toString()).collect(Collectors.joining(",")));
		return obj;
	}

	@AfterReturning(pointcut = "execution(* com.tech.boot.myclient.restclients.*.*(..))", returning = "obj")
	public void logAfterReturning(JoinPoint joinPoint, Object obj) {
		LOGGER.info("executed successfully");
	}

	@AfterReturning(pointcut = "@annotation(com.tech.boot.myclient.annotations.MyAnnotation)", returning = "obj")
	public void logAfterReturning1(JoinPoint joinPoint, Object obj) {
		LOGGER.info("executed successfully");
	}

	@AfterThrowing(pointcut = "execution(* com.tech.boot.myclient.restclients.*.*(..))", throwing = "excep")
	public void logAfterReturning(JoinPoint joinPoint, Throwable excep) throws Throwable {
		LOGGER.error("exception faced {}", excep);
	}

	@AfterThrowing(pointcut = "@annotation(com.tech.boot.myclient.annotations.MyAnnotation)", throwing = "excep")
	public void logAfterReturning1(JoinPoint joinPoint, Throwable excep) throws Throwable {
		LOGGER.error("exception faced {}", excep);
	}

}
