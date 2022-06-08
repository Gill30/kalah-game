package com.gill.kalah.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

@Aspect
@Configuration
public class ServiceLoggingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Around("execution(* com.gill.kalah.service.*.* (..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Object returnObject = joinPoint.proceed();
        logger.info("{} returned with value {}", joinPoint, returnObject);
        return returnObject;
    }
}
