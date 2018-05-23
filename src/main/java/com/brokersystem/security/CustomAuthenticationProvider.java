package com.brokersystem.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brokersystem.controller.BaseController;
import com.brokersystem.request.BaseRequest;
import com.brokersystem.response.BaseResponse;

import javassist.tools.web.BadHttpRequest;

@Aspect
public class CustomAuthenticationProvider{
    
    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    
    private boolean isAuthentificated(BaseRequest req){
        return (Boolean) BaseController.sessionStorage.get(req.getSessionKey()).getAttribute("isAuthorized");
    }
    
    @Around(value="@annotation(com.brokersystem.security.AuthorizedOnly)")
    public Object checAuthorization(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.info("Checking authorization");
        
        Object [] args = joinPoint.getArgs();
        if (args.length != 1){
            throw new BadHttpRequest();
        }
        BaseRequest request = (BaseRequest)args[0];
        try{
            if (isAuthentificated(request)){
                logger.info("Request allowed");
                return joinPoint.proceed();                
            }
            else{
                logger.info("Request forbidden");
                return new BaseResponse("forbidden");
            }
        }
        catch(Exception ex){
            logger.info("Request forbidden");
            return new BaseResponse("session_expired");    
        }
    }
}   