package com.brokersystem.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;


@Aspect
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    
    private void appendArgs(Object[] args, StringBuffer logMessage){
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }
    }
    
    @Around("execution(* com.brokersystem.controller..*(..)) || execution(* com.brokersystem.services..*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable{
        StringBuffer logMessage = new StringBuffer();
        StopWatch stopWatch = new StopWatch();
        
        stopWatch.start();
        Object retVal = joinPoint.proceed();            
        stopWatch.stop();
        
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        
        appendArgs(joinPoint.getArgs(), logMessage);
        logMessage.append(")");
        
        logMessage.append(" execution time: ");
        
        logMessage.append(stopWatch.getTotalTimeMillis());
        
        logMessage.append(" ms");
        
        logger.info(logMessage.toString());
        return retVal;
    }
    
}
