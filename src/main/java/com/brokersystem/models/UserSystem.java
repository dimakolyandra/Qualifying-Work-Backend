package com.brokersystem.models;

import java.sql.Date;
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
@Table(name="USER_SYSTEM")
public class UserSystem {

    @Id
    @Column(name="USER_SYSTEM_ID")
    private Integer userSystemId;
    
    @Column(name="FIRST_NAME")
    private String firstName;
    
    @Column(name="SECOND_NAME")
    private String secondName;
    
    @Column(name="BIRTHDAY")
    private Date birthday;
    
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;
    
    @Column(name="PASSPORT_DATA")
    private String passportData;
    
    @Column(name="LOGIN")
    private String login;
    
    @Column(name="PASSWORD")
    private String password;
    
    @Column(name="USER_TYPE")
    private Integer userType;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="BROKER_FIRM_ID")
    private BrokerFirm brokerFirm;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="trader", cascade=CascadeType.ALL)
    private List<TradingContract> tradersContract;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="broker", cascade=CascadeType.ALL)
    private List<TradingContract> brokersContract;

    public Integer getUserSystemId() {
        return userSystemId;
    }

    public void setUserSystemId(Integer userSystemId) {
        this.userSystemId = userSystemId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public BrokerFirm getBrokerFirm() {
        return brokerFirm;
    }

    public void setBrokerFirm(BrokerFirm brokerFirm) {
        this.brokerFirm = brokerFirm;
    }


    public List<TradingContract> getTradersContract() {
        return tradersContract;
    }

    public void setTradersContract(List<TradingContract> tradersContract) {
        this.tradersContract = tradersContract;
    }

    public List<TradingContract> getBrokersContract() {
        return brokersContract;
    }

    public void setBrokersContract(List<TradingContract> brokersContract) {
        this.brokersContract = brokersContract;
    }

}
