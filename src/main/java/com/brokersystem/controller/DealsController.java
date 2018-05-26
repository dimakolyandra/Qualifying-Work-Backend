package com.brokersystem.controller;

import java.math.BigDecimal;

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

import com.brokersystem.models.UserSystem;
import com.brokersystem.request.BaseRequest;
import com.brokersystem.request.OpenDealRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.security.AuthorizedOnly;
import com.brokersystem.services.PrivateCabinetService;

@Controller
@RequestMapping(value="/deals")
public class DealsController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(Logger.class);
    
    @Autowired
    @Qualifier("privateCabinetService")
    PrivateCabinetService privateCabinetService;
    
    @AuthorizedOnly
    @RequestMapping(value="/new", method=RequestMethod.POST)
    public @ResponseBody BaseResponse openNewDeal(@RequestBody OpenDealRequest openDealRequest){
        try{
            privateCabinetService.openNewDeal(
                    new Integer(Integer.parseInt(openDealRequest.getContractId())),
                    openDealRequest.getSellIso(), 
                    openDealRequest.getBuyIso(),
                    new BigDecimal(openDealRequest.getValue()));
            return new BaseResponse();
        }catch(Exception ex){
            logger.info(ex.getMessage());
            return new BaseResponse("Can not open deal");
        }
    }
    
}
