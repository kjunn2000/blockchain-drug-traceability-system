package com.moody.blockchain;

import com.moody.authentication.UserBank;
import com.moody.digitalSignature.DigitalSignatureImpl;
import com.moody.keygen.KeyAccess;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements Serializable {

    private final int SIZE = 6;

    List<TransactionRecord> tranxLst;

    public Transaction() {
        tranxLst = new ArrayList<>( SIZE );
    }

    public void add(TransactionRecord tranx ){
        String fileName = UserBank.getCurrentUser().getEncryptFullName().replaceAll("/","");
        try {
            PrivateKey privateKey = KeyAccess.getPrivateKey(fileName);
            DigitalSignatureImpl digitalSignature = new DigitalSignatureImpl();
            String cipherText = digitalSignature.sign(tranx.toString(), privateKey);
            tranx.setDigitalSignature(cipherText);
            tranxLst.add(tranx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TransactionRecord> getTranxLst() {
        return tranxLst;
    }

    @Override
    public String toString() {
        return "Transaction [tranxLst=" + tranxLst + "]";
    }

}