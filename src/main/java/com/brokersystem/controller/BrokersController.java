package com.brokersystem.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokersystem.models.BrokerFirm;
import com.brokersystem.response.BaseResponse;

@Controller
@RequestMapping(value="/brokers")
public class BrokersController {
    
    @RequestMapping(method=RequestMethod.GET, value="/list")
    public BaseResponse getBrokersList(){
        return new BaseResponse();
    }
}
