package com.brokersystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brokersystem.models.UserSystem;
import com.brokersystem.request.RegistrationRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.response.UsersCountResponse;
import com.brokersystem.services.AuthorizationService;

@Controller
@RequestMapping(value="/users")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    
    @Autowired
    @Qualifier("authorizationService")
    AuthorizationService authorizationService;
    
    @RequestMapping(value="/count", method=RequestMethod.POST, produces = "application/json")
    public @ResponseBody UsersCountResponse getUsersCountByLogin(@RequestBody String userLogin){
        int countUsersWithLogin = authorizationService.getUsersCountByLogin(userLogin);
        return new UsersCountResponse(countUsersWithLogin); 
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST, produces="application/json")
    public @ResponseBody BaseResponse registerUser(@RequestBody RegistrationRequest registrationRequest){
        try{
            authorizationService.registerUser(registrationRequest.getNewUser(), registrationRequest.getBrokerId());
            return new BaseResponse();
        }
        catch(Exception ex){
            logger.info("ERROR: " + ex.getMessage());
            return new BaseResponse("Authorisation servie error");
        }
    }
    
    
}
