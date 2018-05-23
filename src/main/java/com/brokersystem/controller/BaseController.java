package com.brokersystem.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class BaseController {
    
    static public HashMap<String, HttpSession> sessionStorage = new HashMap<String, HttpSession>();
}
