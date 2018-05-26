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

import com.brokersystem.models.UserSystem;
import com.brokersystem.request.LoginRequest;
import com.brokersystem.request.RegistrationRequest;
import com.brokersystem.request.UsersCountRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.response.LoginResponse;
import com.brokersystem.response.UsersCountResponse;
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
        int countUsersWithLogin = authorizationService.getUsersCountByLogin(userLogin.getLogin());
        return new UsersCountResponse(countUsersWithLogin); 
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public @ResponseBody BaseResponse registerUser(
            @RequestBody RegistrationRequest registrationRequest){
        String uuid = registrationRequest.getSessionKey();
        try{
            authorizationService.registerUser(
                    registrationRequest.getNewUser(), 
                    registrationRequest.getBrokerId());
            HttpSession session = sessionStorage.get(uuid);
            session.setAttribute("userId", registrationRequest.getNewUser());
            session.setAttribute("isAuthorized", true);
            return new BaseResponse();
        }catch(Exception ex){
        	logger.info(ex.getMessage());
            return new BaseResponse(ex.getMessage());
        }
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody BaseResponse loginUser(
            @RequestBody LoginRequest loginRequest){
        try {
            String uuid = loginRequest.getSessionKey();
            UserSystem loggedUser = authorizationService.login(
                    loginRequest.getLogin(), 
                    loginRequest.getPassword());
            HttpSession session = sessionStorage.get(uuid);
            session.setAttribute("userId", loggedUser.getUserSystemId());
            session.setAttribute("isAuthorized", true);           
            return new LoginResponse(loggedUser.getFirstName(), loggedUser.getSecondName());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new BaseResponse("Can not autotificate");
        }
    }
    
}
