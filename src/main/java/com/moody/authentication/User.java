package com.moody.authentication;

import com.moody.blockchain.BusinessType;

import java.security.KeyPair;

public class User {
    private String fullName;
    private String password;
    private String companyName;
    private String companyLocation;
    private BusinessType businessType;
    private String encryptFullName;

    public User(String fullName, String password, String companyName, String companyLocation, BusinessType businessType) {
        this.fullName = fullName;
        this.password = password;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.businessType = businessType;
    }

    public User(String fullName, String password, String companyName, String companyLocation, BusinessType businessType, String encryptFullName) {
        this.fullName = fullName;
        this.password = password;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.businessType = businessType;
        this.encryptFullName = encryptFullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEncryptFullName() {
        return encryptFullName;
    }

    public void setEncryptFullName(String encryptFullName) {
        this.encryptFullName = encryptFullName;
    }

    @Override
    public String toString() {
        return fullName+","+password+","+companyName+","+companyLocation+","+businessType+","+encryptFullName;
    }
}
