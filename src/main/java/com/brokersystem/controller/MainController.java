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
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView main(){
	    return new ModelAndView("home");
	}
	
}
