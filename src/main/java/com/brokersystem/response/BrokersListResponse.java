package com.brokersystem.response;

import java.util.List;

import com.brokersystem.models.BrokerFirm;

public class BrokersListResponse extends BaseResponse{
    private List<BrokerFirm> firms;
    
    public BrokersListResponse(List<BrokerFirm> firms){
        super();
        this.firms = firms;
    }

    public List<BrokerFirm> getFirms() {
        return firms;
    }

    public void setFirms(List<BrokerFirm> firms) {
        this.firms = firms;
    }
    
}
