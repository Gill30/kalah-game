package com.gill.kalah.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

@Aspect
@Configuration
public class ServiceLoggingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //execution(* PACKAGE.*.* (..))
    public void around(JoinPoint joinPoint, Object result){
        logger.info("{} returned with value {}");
    }
}
