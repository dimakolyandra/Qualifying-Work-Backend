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
    
    @Column(name="DATE_QUESTION")
    private Date dateQuestion;
    
    @Column(name="TEXT_QUESTION")
    private String textQuestion;
    
    @Column(name="AUTHOR_FLAG")
    private Integer authorFlag;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TRADING_CONTRACT_ID")
    private TradingContract contract;

    public Integer getTradersQuestionsId() {
        return tradersQuestionsId;
    }

    public void setTradersQuestionsId(Integer tradersQuestionsId) {
        this.tradersQuestionsId = tradersQuestionsId;
    }

    public Date getDateQuestion() {
        return dateQuestion;
    }

    public void setDateQuestion(Date date) {
        this.dateQuestion = date;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String text) {
        this.textQuestion = text;
    }

    public Integer getAuthorFlag() {
        return authorFlag;
    }

    public void setAuthorFlag(Integer authorType) {
        this.authorFlag = authorType;
    }

    public TradingContract getContract() {
        return contract;
    }

    public void setContract(TradingContract contract) {
        this.contract = contract;
    }
    
}
