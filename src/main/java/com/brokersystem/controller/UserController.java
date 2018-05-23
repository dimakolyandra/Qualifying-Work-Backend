package com.brokersystem.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.brokersystem.request.LoginRequest;
import com.brokersystem.request.RegistrationRequest;
import com.brokersystem.request.UsersCountRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.response.UsersCountResponse;
import com.brokersystem.security.AuthorizedOnly;
import com.brokersystem.services.AuthorizationService;

@Controller
@RequestMapping(value="/users", produces = "application/json")
@SessionAttributes(value="sessionKey")
public class UserController extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;
    
    @RequestMapping(value="/count", method=RequestMethod.POST)
    public @ResponseBody BaseResponse getUsersCountByLogin(
            @RequestBody UsersCountRequest userLogin){
        logger.info(userLogin.getSessionKey());
        logger.info(sessionStorage.get(userLogin.getSessionKey()).getAttribute("isAuthorized").toString());
        int countUsersWithLogin = authorizationService.getUsersCountByLogin(userLogin.getLogin());
        return new UsersCountResponse(countUsersWithLogin); 
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public @ResponseBody BaseResponse registerUser(@RequestBody RegistrationRequest registrationRequest){
        authorizationService.registerUser(registrationRequest.getNewUser(), registrationRequest.getBrokerId());
        return new BaseResponse();
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody BaseResponse loginUser(@RequestBody LoginRequest loginRequest){
        return null;
        
    }
    
}
