package com.moody.blockchain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionRecord implements Serializable {

    private UUID transactionId;
    private TransactionType type;
    private String personInCharge;
    private String companyName;
    private String companyLocation;
    private BusinessType businessType;
    private LocalDateTime dateTime;
    private String digitalSignature;

    public TransactionRecord(TransactionType type, String personInCharge, String companyName, String companyLocation, BusinessType businessType) {
        this.transactionId = UUID.randomUUID();
        this.type = type;
        this.personInCharge = personInCharge;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.businessType = businessType;
        this.dateTime = LocalDateTime.now();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "transactionId=" + transactionId +
                ", type=" + type +
                ", personInCharge='" + personInCharge + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                ", businessType=" + businessType +
                ", dateTime=" + dateTime +
                '}';
    }
}
