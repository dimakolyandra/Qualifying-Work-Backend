package com.brokersystem.request;

public class ContractListRequest extends BaseRequest{
    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    
}
