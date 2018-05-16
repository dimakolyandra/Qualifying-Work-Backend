package com.brokersystem.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CURRENCY")
public class Currency {
    
    @Id
    @Column(name="ISO")
    private String ISO;
    
    @Column(name="STATE")
    private String state;
    
    @Column(name="CURRENCY_NAME")
    private String currencyName;
    
    @Column(name="ISSUING_CENTER")
    private String issuingCenter;
    
    @Column(name="VARIABLE_CURRENCY")
    private String variableCurrency;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="accountCurrency", cascade=CascadeType.ALL)
    private List<TraderAccount> accounts;

    public String getISO() {
        return ISO;
    }

    public void setISO(String iSO) {
        ISO = iSO;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getIssuingCenter() {
        return issuingCenter;
    }

    public void setIssuingCenter(String issuingCenter) {
        this.issuingCenter = issuingCenter;
    }

    public String getVariableCurrency() {
        return variableCurrency;
    }

    public void setVariableCurrency(String variableCurrency) {
        this.variableCurrency = variableCurrency;
    }

    public List<TraderAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<TraderAccount> accounts) {
        this.accounts = accounts;
    }

}
