package com.brokersystem.response;

public class DealResponse{
    private String id;
    private String dateOpen;
    private String dateClose;
    private String value;
    private String sellAccount;
    private String buyAccount;
    private String sellIso;
    private String buyIso;
    private String brokersData;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDateOpen() {
        return dateOpen;
    }
    public void setDateOpen(String date) {
        this.dateOpen = date;
    }
    public String getDateClose() {
        return dateClose;
    }
    public void setDateClose(String date) {
        this.dateClose = date;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getSellAccount() {
        return sellAccount;
    }
    public void setSellAccount(String sellAccount) {
        this.sellAccount = sellAccount;
    }
    public String getBuyAccount() {
        return buyAccount;
    }
    public void setBuyAccount(String buyAccount) {
        this.buyAccount = buyAccount;
    }
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
    public String getBrokersData() {
        return brokersData;
    }
    public void setBrokersData(String brokersData) {
        this.brokersData = brokersData;
    }
    
        
}
