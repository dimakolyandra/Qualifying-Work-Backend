package com.brokersystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brokersystem.request.ContractListRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.security.AuthorizedOnly;
import com.brokersystem.services.PrivateCabinetService;

@Controller
@RequestMapping(value="/contracts")
public class ContractsController extends BaseController{

    @Autowired
    @Qualifier("privateCabinetService")
    PrivateCabinetService privateCabinetService;

    @AuthorizedOnly
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public @ResponseBody BaseResponse getContractsList(
            @RequestBody ContractListRequest getContractsRequest){
        String uuid = getContractsRequest.getSessionKey();
        HttpSession session = sessionStorage.get(uuid);
        Integer userId = (Integer) session.getAttribute("userId");
        return privateCabinetService.getDataForNewDeal(userId);
    }
}
