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

import com.brokersystem.request.ContractListRequest;
import com.brokersystem.request.ContractRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.security.AuthorizedOnly;
import com.brokersystem.services.PrivateCabinetService;

@Controller
@RequestMapping(value="/contracts")
public class ContractsController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(Logger.class);

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
    
    @AuthorizedOnly
    @RequestMapping(value="/new", method=RequestMethod.POST)
    public @ResponseBody BaseResponse addNewContract(
    		@RequestBody ContractRequest newContractRequest){
    	String uuid = newContractRequest.getSessionKey();
    	HttpSession session = sessionStorage.get(uuid);
    	Integer userId = (Integer) session.getAttribute("userId");
    	try{
    		privateCabinetService.addContract(userId, newContractRequest.getContractOrFirmId());
    		return new BaseResponse();
    	}catch(Exception ex){
    		return new BaseResponse("Can not add new contract!");
    	}
    }
    
    @AuthorizedOnly
    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public @ResponseBody BaseResponse removeContract(
    		@RequestBody ContractRequest newContractRequest){
    	try{
    		boolean isSetClose = privateCabinetService.removeContract(newContractRequest.getContractOrFirmId());
    		if (isSetClose)
    			return new BaseResponse();
    		else
    			return new BaseResponse("Contract has opened deals");
    	}catch(Exception ex){
    		logger.info(ex.getMessage());
    		return new BaseResponse("Can not remove contract!");
    	}
    }
}
