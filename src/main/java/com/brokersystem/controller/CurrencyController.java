package com.brokersystem.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brokersystem.request.QuotationsRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.services.PrivateCabinetService;

@Controller
public class CurrencyController extends BaseController{
	
    @Autowired
    @Qualifier("privateCabinetService")
    PrivateCabinetService privateCabinetService;

	@RequestMapping(value="/currency/quotations", method=RequestMethod.POST)
	public @ResponseBody BaseResponse getQuotations(@RequestBody QuotationsRequest quotReq) throws IOException{
		return privateCabinetService.getCurrQuot(quotReq.getCurrId());
	}
}
