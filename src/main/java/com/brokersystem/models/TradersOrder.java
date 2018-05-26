package com.brokersystem.models;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TRADERS_ORDER")
public class TradersOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_GENERATOR")
    @SequenceGenerator(name="ORDER_GENERATOR", sequenceName = "GET_ID_TRADERS_ORDER", allocationSize=1)
    @Column(name="TRADERS_ORDER_ID")
    private Integer tradersOrderId;
    
    @Column(name="DATE_BEGIN")
    private Date dateBegin;
    
    @Column(name="VALUE")
    private BigDecimal value;
    
    @Column(name="STATUS")
    private String status;
    
    @Column(name="DATE_END")
    private Date dateEnd;
    
    @ManyToOne
    @JoinColumn(name="FIRST_ACCOUNT_ID")
    private TraderAccount sellAccount;
    
    @ManyToOne
    @JoinColumn(name="SECOND_ACCOUNT_ID")
    private TraderAccount buyAccount;
    
    @ManyToOne
    @JoinColumn(name="TRADING_CONTRACT_ID")
    private TradingContract contract;

    public Integer getTradersOrderId() {
        return tradersOrderId;
    }

    public void setTradersOrderId(Integer tradersOrderId) {
        this.tradersOrderId = tradersOrderId;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public TraderAccount getSellAccount() {
        return sellAccount;
    }

    public void setSellAccount(TraderAccount sellAccount) {
        this.sellAccount = sellAccount;
    }

    public TraderAccount getBuyAccount() {
        return buyAccount;
    }

    public void setBuyAccount(TraderAccount buyAccount) {
        this.buyAccount = buyAccount;
    }

    public TradingContract getContract() {
        return contract;
    }

    public void setContract(TradingContract contract) {
        this.contract = contract;
    }

}
