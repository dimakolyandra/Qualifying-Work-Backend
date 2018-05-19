package com.brokersystem.response;

public class UsersCountResponse extends BaseResponse{
    private int userCount;
    
    public UsersCountResponse(int userCount){
        this.userCount = userCount;
    }
    
    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    
}
