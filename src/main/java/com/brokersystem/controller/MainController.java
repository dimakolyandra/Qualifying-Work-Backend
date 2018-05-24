package com.brokersystem.controller;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brokersystem.response.SessionKeyResponse;


@Controller
public class MainController extends BaseController{

    @RequestMapping(value="/getsessionkey", method=RequestMethod.GET)
    public @ResponseBody SessionKeyResponse main(HttpSession httpSession){
        KeyGenerator gen;
        String uuid = UUID.randomUUID().toString();
        try {
            gen = KeyGenerator.getInstance("AES");
            gen.init(128);
            SecretKey secret = gen.generateKey();
            byte[] binary = secret.getEncoded();
            String generatedString = String.format("%032X", new BigInteger(+1, binary));
            httpSession.setAttribute("aesKey", secret);
            httpSession.setAttribute("isAuthorized", false);
            httpSession.setAttribute("user", null);
            sessionStorage.put(uuid, httpSession);
            return new SessionKeyResponse(uuid + ":" + generatedString);
        } catch (NoSuchAlgorithmException e) {
            return new SessionKeyResponse(null, "error");
        }
 	}
	
}
