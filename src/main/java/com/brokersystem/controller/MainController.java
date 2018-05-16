package com.brokersystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.brokersystem.dao.BaseDAO;
import com.brokersystem.models.Currency;
import com.brokersystem.models.UserSystem;

@Controller
public class MainController{
	
	private final static Logger logger = LoggerFactory.getLogger(Logger.class);
	
	@Autowired
	@Qualifier("userSystemDao")
	BaseDAO<UserSystem, Integer> test;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView main(){
	    //159 - два брокера
	    UserSystem usr = test.getObj(new Integer(159));
	    logger.info("FIRSTNAME: " + usr.getFirstName());
	    for(int i = 0; i < usr.getTradersContract().size(); i++){
	        logger.info("CONTRACTS_TRD: " + usr.getTradersContract().get(i).getTradingContractId().toString());
	        UserSystem brk = usr.getTradersContract().get(i).getBroker();
	        logger.info("BRKNAME: " + brk.getFirstName());
            logger.info("BRKSECNAME: " + brk.getSecondName());
            logger.info("CONTRACTS_BRK: " + brk.getBrokersContract().get(0).getTradingContractId().toString());
	    }
	    return new ModelAndView("home");
	}
	
}
