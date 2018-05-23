package com.brokersystem.response;

public class SessionKeyResponse extends BaseResponse{

    private String sessionKey;
    
    public SessionKeyResponse(){}
    
    public SessionKeyResponse(String sessionKey){
        this.sessionKey = sessionKey;
    }

    public SessionKeyResponse(String sessionKey, String status){
        super(status);
        this.sessionKey = "No such key";
    }
    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
    
}
