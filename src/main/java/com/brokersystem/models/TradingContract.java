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
@Table(name="TRADING_CONTRACT")
public class TradingContract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACT_GENERATOR")
    @SequenceGenerator(name="CONTRACT_GENERATOR", sequenceName = "GET_ID_TRADING_CONTRACT", allocationSize=1)
    @Column(name="TRADING_CONTRACT_ID")
    private Integer tradingContractId;
    
    @ManyToOne
    @JoinColumn(name="TRADER_USER_ID")
    private UserSystem trader;

    @ManyToOne
    @JoinColumn(name="BROKER_USER_ID")
    private UserSystem broker;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="contract", cascade=CascadeType.ALL)
    private List<TraderAccount> accounts;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="contract", cascade=CascadeType.ALL)
    private List<TradersQuestions> tradersQuestions;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="contract", cascade=CascadeType.ALL)
    private List<TradersOrder> tradersOrder;

    public Integer getTradingContractId() {
        return tradingContractId;
    }

    public void setTradingContractId(Integer tradingContractId) {
        this.tradingContractId = tradingContractId;
    }

    public UserSystem getTrader() {
        return trader;
    }

    public void setTrader(UserSystem trader) {
        this.trader = trader;
    }

    public UserSystem getBroker() {
        return broker;
    }

    public void setBroker(UserSystem broker) {
        this.broker = broker;
    }

    public List<TraderAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<TraderAccount> accounts) {
        this.accounts = accounts;
    }

    public List<TradersQuestions> getTradersQuestions() {
        return tradersQuestions;
    }

    public void setTradersQuestions(List<TradersQuestions> tradersQuestions) {
        this.tradersQuestions = tradersQuestions;
    }

    public List<TradersOrder> getTradersOrder() {
        return tradersOrder;
    }

    public void setTradersOrder(List<TradersOrder> tradersOrder) {
        this.tradersOrder = tradersOrder;
    }

}
