package com.brokersystem.request;

import java.math.BigDecimal;

import com.brokersystem.models.TradingContract;

public class OpenDealRequest extends BaseRequest{
    private String sellIso;
    private String buyIso;
    private String value;
    private String contractId;
    
    public String getSellIso() {
        return sellIso;
    }
    public void setSellIso(String sellIso) {
        this.sellIso = sellIso;
    }
    public String getBuyIso() {
        return buyIso;
    }
    public void setBuyIso(String buyIso) {
        this.buyIso = buyIso;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getContractId() {
        return contractId;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
