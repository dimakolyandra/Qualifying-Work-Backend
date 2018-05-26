package com.brokersystem.response;

import java.util.List;

public class DealResponseWrapper extends BaseResponse{
    private List<DealResponse> deals;
    private Integer dealsCount;
    
    public DealResponseWrapper(List<DealResponse> deals, Integer dealCount){
        this.deals = deals;
        this.dealsCount = dealCount;
    }
    
    public List<DealResponse> getDeals() {
        return deals;
    }
    public void setDeals(List<DealResponse> deals) {
        this.deals = deals;
    }
    public Integer getDealsCount() {
        return dealsCount;
    }
    public void setDealsCount(Integer dealsCount) {
        this.dealsCount = dealsCount;
    }
}
