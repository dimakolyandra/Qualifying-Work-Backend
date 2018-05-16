package com.brokersystem.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TRADER_ACCOUNT")
public class TraderAccount {

    @Id
    @Column(name="TRADER_ACCOUNT_ID")
    private Integer traderAccountId;
    
    @Column(name="STATUS_ACCOUNT")
    private Integer statusAccount;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TRADING_CONTRACT_ID")
    private TradingContract contract;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CURR_ISO")
    private Currency accountCurrency;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="sellAccount", cascade=CascadeType.ALL)
    private List<TradersOrder> sellOrders;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="buyAccount", cascade=CascadeType.ALL)
    private List<TradersOrder> buyAccount;

    public List<TradersOrder> getBuyAccount() {
        return buyAccount;
    }

    public void setBuyAccount(List<TradersOrder> buyAccount) {
        this.buyAccount = buyAccount;
    }

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


}
