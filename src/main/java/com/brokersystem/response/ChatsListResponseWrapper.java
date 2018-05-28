package com.brokersystem.response;

import java.util.List;

public class ChatsListResponseWrapper extends BaseResponse{
	private List<ChatsListResponse> messages;
	
	public ChatsListResponseWrapper(List<ChatsListResponse> resp){
		messages = resp;
	}
	
	public List<ChatsListResponse> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatsListResponse> messages) {
		this.messages = messages;
	}
	
}
