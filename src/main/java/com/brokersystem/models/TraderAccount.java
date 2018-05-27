package com.brokersystem.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TRADER_ACCOUNT")
public class TraderAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ID")
    @SequenceGenerator(name="ACCOUNT_ID", sequenceName = "GET_ID_TRADER_ACCOUNT", allocationSize=1)
    @Column(name="TRADER_ACCOUNT_ID")
    private Integer traderAccountId;
    
    @Column(name="STATUS_ACCOUNT")
    private Integer statusAccount;
    
    @Column(name="ACCOUNT_NUMBER")
    private String accountNumber;
    
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    @JoinColumn(name="TRADING_CONTRACT_ID")
    private TradingContract contract;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CURR_ISO")
    private Currency accountCurrency;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="sellAccount", orphanRemoval=true, cascade=CascadeType.REMOVE)
    private List<TradersOrder> sellOrders;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="buyAccount", orphanRemoval=true, cascade=CascadeType.REMOVE)
    private List<TradersOrder> buyAccount;

    public Integer getTraderAccountId() {
        return traderAccountId;
    }

    public void setTraderAccountId(Integer traderAccountId) {
        this.traderAccountId = traderAccountId;
    }

    public Integer getStatusAccount() {
        return statusAccount;
    }

    public void setStatusAccount(Integer statusAccount) {
        this.statusAccount = statusAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TradingContract getContract() {
        return contract;
    }

    public void setContract(TradingContract contract) {
        this.contract = contract;
    }

    public Currency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(Currency accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public List<TradersOrder> getSellOrders() {
        return sellOrders;
    }

    public void setSellOrders(List<TradersOrder> sellOrders) {
        this.sellOrders = sellOrders;
    }

    public List<TradersOrder> getBuyAccount() {
        return buyAccount;
    }

    public void setBuyAccount(List<TradersOrder> buyAccount) {
        this.buyAccount = buyAccount;
    }



}
