package com.brokersystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brokersystem.request.BaseRequest;
import com.brokersystem.request.GetDialogRequest;
import com.brokersystem.request.NewMessageRequest;
import com.brokersystem.response.BaseResponse;
import com.brokersystem.services.PrivateCabinetService;

@Controller
@RequestMapping(value="/chats")
public class ChatsController extends BaseController{

    @Autowired
    @Qualifier("privateCabinetService")
    PrivateCabinetService privateCabinetService;

    @RequestMapping(value="/list")
	public @ResponseBody BaseResponse getChatsList(@RequestBody BaseRequest getChatsRequest){
		try{
			HttpSession session = sessionStorage.get(getChatsRequest.getSessionKey());
            Integer userId = (Integer) session.getAttribute("userId");
			return privateCabinetService.getChatsList(userId);
		}catch(Exception ex){
			return new BaseResponse("Can not get chats");
		}
	}
    
    @RequestMapping(value="/dialog")
    public @ResponseBody BaseResponse getDialogData(@RequestBody GetDialogRequest getDialogRequest){
		try{
			return privateCabinetService.getDialog(getDialogRequest.getContractId());
		}catch(Exception ex){
			return new BaseResponse("Can not get dialogs");
		}
    }
    
    @RequestMapping(value="/new")
    public @ResponseBody BaseResponse addNewMessage(@RequestBody NewMessageRequest newMessageRequest){
		try{
			privateCabinetService.addMessage(
				newMessageRequest.getText(), 
				newMessageRequest.getContractId());
			return new BaseResponse();
		}catch(Exception ex){
			return new BaseResponse("Can not get dialogs");
		}    	
    }
	
}
