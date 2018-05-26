package com.brokersystem.request;

public class GetDealRequest extends BaseRequest{
    
    private String startInd;
    private String endInd;
    
    public String getStartInd() {
        return startInd;
    }
    
    public void setStartInd(String startInd) {
        this.startInd = startInd;
    }
    
    public String getEndInd() {
        return endInd;
    }
    
    public void setEndInd(String endInd) {
        this.endInd = endInd;
    }

}
