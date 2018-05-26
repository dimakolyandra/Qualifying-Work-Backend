package com.brokersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brokersystem.models.BrokerFirm;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.response.BrokersListResponse;
import com.brokersystem.services.PrivateCabinetService;

@Controller
@RequestMapping(value="/brokers")
public class BrokersController extends BaseController{

    @Autowired
    @Qualifier("privateCabinetService")
    PrivateCabinetService privateCabinetService;

    @RequestMapping(method=RequestMethod.GET, value="/list")
    public @ResponseBody BaseResponse getBrokersList(){
        List<BrokerFirm> brokers = privateCabinetService.getBrokersList();
        return new BrokersListResponse(brokers);
    }
}
