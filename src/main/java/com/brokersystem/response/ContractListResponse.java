package com.brokersystem.response;

import java.util.HashMap;
import java.util.List;

import com.brokersystem.models.TradingContract;

public class ContractListResponse extends BaseResponse{
    private List<HashMap<String, String>> contracts;
    
    
    public ContractListResponse(List<HashMap<String, String>> contracts){
        this.contracts = contracts;
    }
    
    public List<HashMap<String, String>> getContracts() {
        return contracts;
    }

    public void setContracts(List<HashMap<String, String>> contracts) {
        this.contracts = contracts;
    }
    
}
