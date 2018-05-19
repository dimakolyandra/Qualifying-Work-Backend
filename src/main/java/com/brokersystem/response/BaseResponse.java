package com.brokersystem.response;

public class BaseResponse {
    
    private String status;
        
    public BaseResponse(){
        this.status = "ok";
    }

    public BaseResponse(String status){
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
