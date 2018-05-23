package com.brokersystem.request;

public class UsersCountRequest extends BaseRequest{
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
}
