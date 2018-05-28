package com.brokersystem.request;

public class NewMessageRequest extends BaseRequest{
	private String text;
	private Integer contractId;
	
	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
