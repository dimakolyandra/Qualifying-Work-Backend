package com.brokersystem.request;

import com.brokersystem.models.UserSystem;

public class RegistrationRequest{
    
    private UserSystem newUser;
    private Integer brokerId;
   
    public UserSystem getNewUser() {
        return newUser;
    }
    public void setNewUser(UserSystem newUser) {
        this.newUser = newUser;
    }
    public Integer getBrokerId() {
        return brokerId;
    }
    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }
    
}
