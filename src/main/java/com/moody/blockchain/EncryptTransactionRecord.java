package com.moody.blockchain;

public class EncryptTransactionRecord {
    private String encryptFullName;
    private String encryptData;

    public EncryptTransactionRecord(String encryptFullName, String encryptData) {
        this.encryptFullName = encryptFullName;
        this.encryptData = encryptData;
    }

    public String getEncryptFullName() {
        return encryptFullName;
    }

    public void setEncryptFullName(String encryptFullName) {
        this.encryptFullName = encryptFullName;
    }

    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }
}
