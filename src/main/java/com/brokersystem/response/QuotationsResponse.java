package com.brokersystem.response;

public class QuotationsResponse extends BaseResponse{
	private Double y;
	
	public QuotationsResponse(Double y) {
		this.y = y;
	}
	
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	
	
}
