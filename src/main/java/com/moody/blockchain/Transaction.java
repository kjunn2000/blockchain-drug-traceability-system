package com.moody.blockchain;

import com.moody.authentication.UserBank;
import com.moody.crypto.ASymmCrypto;
import com.moody.keygen.KeyAccess;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements Serializable {

    private final int SIZE = 4;

    List<EncryptTransactionRecord> tranxLst;

    public Transaction() {
        tranxLst = new ArrayList<>( SIZE );
    }

    public void add(TransactionRecord tranx ){
        String fileName = UserBank.getCurrentUser().getEncryptFullName();
        try {
            PublicKey publicKey = KeyAccess.getPublicKey(fileName);
            ASymmCrypto aSymmCrypto = new ASymmCrypto();
            String cipherText = aSymmCrypto.encrypt(tranx, publicKey);
            tranxLst.add(new EncryptTransactionRecord(fileName, cipherText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Transaction [tranxLst=" + tranxLst + "]";
    }

}