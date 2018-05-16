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
@Table(name="BROKER_FIRM")
public class BrokerFirm {
    
    @Id
    @Column(name="BROKER_FIRM_ID")
    private Integer brokerFirmId;
    
    @Column(name="FIRM_NAME")
    private String firmName;
    
    @Column(name="STATE_REGISTRATION_NUMBER")
    private String stateRegistrationNumber;
    
    @Column(name="INDIVIDUAL_TAXPAYER_INDEX")
    private String individualTaxpayerIndex;
    
    @Column(name="AVATAR_URL")
    private String avatarUrl;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="brokerFirm", cascade=CascadeType.ALL)
    private List<UserSystem> firmWorkers;

    public Integer getBrokerFirmId() {
        return brokerFirmId;
    }

    public void setBrokerFirmId(Integer brokerFirmId) {
        this.brokerFirmId = brokerFirmId;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getStateRegistrationNumber() {
        return stateRegistrationNumber;
    }

    public void setStateRegistrationNumber(String stateRegistrationNumber) {
        this.stateRegistrationNumber = stateRegistrationNumber;
    }

    public String getIndividualTaxpayerIndex() {
        return individualTaxpayerIndex;
    }

    public void setIndividualTaxpayerIndex(String individualTaxpayerIndex) {
        this.individualTaxpayerIndex = individualTaxpayerIndex;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<UserSystem> getFirmWorkers() {
        return firmWorkers;
    }

    public void setFirmWorkers(List<UserSystem> firmWorkers) {
        this.firmWorkers = firmWorkers;
    }
}
