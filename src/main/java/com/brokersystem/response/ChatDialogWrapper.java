package com.brokersystem.response;

import java.util.List;
import java.util.Map;

public class ChatDialogWrapper extends BaseResponse{
	private Map<String, String> header;
	private List<ChatDialogResponse> content;
	
	public ChatDialogWrapper(Map<String, String> header, List<ChatDialogResponse> content){
		this.header = header;
		this.content = content;
	}
	
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	public List<ChatDialogResponse> getContent() {
		return content;
	}
	public void setContent(List<ChatDialogResponse> content) {
		this.content = content;
	}
	
	
}
