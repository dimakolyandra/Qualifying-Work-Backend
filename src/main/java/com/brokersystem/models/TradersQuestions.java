package com.brokersystem.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TRADERS_QUESTIONS")
public class TradersQuestions {

    @Id
    @Column(name="TRADERS_QUESTIONS_ID")
    private Integer tradersQuestionsId;
    
    @Column(name="DATE")
    private Date date;
    
    @Column(name="TEXT")
    private String text;
    
    @Column(name="AUTHOR_TYPE")
    private Integer authorType;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TRADING_CONTRACT_ID")
    private TradingContract contract;

    public Integer getTradersQuestionsId() {
        return tradersQuestionsId;
    }

    public void setTradersQuestionsId(Integer tradersQuestionsId) {
        this.tradersQuestionsId = tradersQuestionsId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAuthorType() {
        return authorType;
    }

    public void setAuthorType(Integer authorType) {
        this.authorType = authorType;
    }

    public TradingContract getContract() {
        return contract;
    }

    public void setContract(TradingContract contract) {
        this.contract = contract;
    }
    
}
