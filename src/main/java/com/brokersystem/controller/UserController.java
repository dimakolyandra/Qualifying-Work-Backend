package com.brokersystem.controller;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
import com.brokersystem.response.UsersCountResponse;
import com.brokersystem.services.AuthorizationService;

@Controller
@RequestMapping(value="/users", produces = "application/json")
@SessionAttributes(value="sessionKey")
public class UserController extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    private static final String ALGORITHM = "AES";
    
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
            session.setAttribute("user", registrationRequest.getNewUser());
            session.setAttribute("isAuthorized", true);
            return new BaseResponse();
        }catch(Exception ex){
            return new BaseResponse("Can not register");
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
            session.setAttribute("user", loggedUser);
            session.setAttribute("isAuthorized", true);            
            return new BaseResponse();
        } catch (Exception e) {
            return new BaseResponse("Can not autotificate");
        }
    }
    
}
