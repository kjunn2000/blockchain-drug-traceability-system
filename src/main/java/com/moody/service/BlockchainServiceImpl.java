package com.moody.service;

import com.moody.authentication.User;
import com.moody.authentication.UserBank;
import com.moody.blockchain.*;
import com.moody.crypto.ASymmCrypto;
import com.moody.crypto.SymmCrypto;
import com.moody.digitalSignature.DigitalSignature;
import com.moody.digitalSignature.DigitalSignatureImpl;
import com.moody.keygen.KeyAccess;
import com.moody.keygen.SecretCharsKeyGen;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;
import java.util.Optional;

public class BlockchainServiceImpl implements BlockchainService{

    @Override
    public boolean addTransactionRecord(String drugId, TransactionType type) {
        TransactionRecord record= new TransactionRecord(type,
                UserBank.getCurrentUser().getFullName(),
                UserBank.getCurrentUser().getCompanyName(),
                UserBank.getCurrentUser().getCompanyLocation(),
                UserBank.getCurrentUser().getBusinessType());

        Block target = null;
        int count = 0;
        for (Block block : Blockchain.DB){
            if(Objects.isNull(block.getDrug())){
                count++;
                continue;
            }
            if (block.getDrug().getDrugId().toString().equals(drugId)){
                target = block;
                break;
            }
            count++;
        }
        if (Objects.isNull(target)){
            return false;
        }
        Transaction newTranx = target.getTranx();
        newTranx.add(record);
        target.setTranx(newTranx);
        if (type == TransactionType.RECEIVED){
            switch(UserBank.getCurrentUser().getBusinessType()){
                case MANUFACTURER: {
                    target.getDrug().setStatus(DrugStatus.MANUFACTURING);
                    break;
                }
                case DISTRIBUTOR: {
                    target.getDrug().setStatus(DrugStatus.DISTRIBUTING);
                    break;
                }
                case PHARMACY: {
                    target.getDrug().setStatus(DrugStatus.RECEIVED);
                    break;
                }
            }
        }
        Blockchain.mining(count+1, target.getHeader().getCurrentHash());
        Blockchain.persist();
        Blockchain.distribute();
        return true;
    }

    @Override
    public boolean addNewDrug(Drug drug, User userToSign) {
        String encryptUnitPrice = encryptUnitPrice(drug.getUnitPrice(), userToSign);
        if (Objects.isNull(encryptUnitPrice)){
            return false;
        }
        drug.setUnitPrice(encryptUnitPrice);
        Block prevBlock = Objects.requireNonNull(Blockchain.get()).getLast();
        Block newBlock = new Block( prevBlock.getHeader().getCurrentHash(), drug);
        newBlock.getHeader().setIndex(prevBlock.getHeader().getIndex()+1);
        Transaction newTranx = new Transaction();
        newTranx.add(new TransactionRecord(TransactionType.SEND,
                UserBank.getCurrentUser().getFullName(),
                UserBank.getCurrentUser().getCompanyName(),
                UserBank.getCurrentUser().getBusinessType().toString(),
                BusinessType.SUPPLIER));
        newBlock.setTranx(newTranx);
        Blockchain.nextBlock( newBlock);
        Blockchain.distribute();
        return true;
    }

    @Override
    public Block findBlock(String drugId) {
        Optional<Block> block = Blockchain.findBlock(drugId);
        if(block.isEmpty()){
            return null;
        }
        return block.get();
    }

    @Override
    public String decryptUnitPrice(String cipherText) {
        try{
            String fileName = UserBank.getCurrentUser().getEncryptFullName().replaceAll("/", "");
            PrivateKey privateKey = KeyAccess.getPrivateKey(fileName);
            ASymmCrypto aSymmCrypto = new ASymmCrypto();
            return aSymmCrypto.decrypt(cipherText, privateKey);
        }catch (Exception e){
            return cipherText;
        }
    }

    private String encryptUnitPrice(String unitPrice, User manufacturer){
        String fileName = manufacturer.getEncryptFullName().replaceAll("/","");
        try {
            PublicKey publicKey =  KeyAccess.getPublicKey(fileName);
            ASymmCrypto aSymmCrypto = new ASymmCrypto();
            return aSymmCrypto.encrypt(unitPrice, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean verifyDigitalSignature(TransactionRecord record) {
        String fileName = getKeyFileNameByUserFullName(record.getPersonInCharge());
        DigitalSignature digitalSignature = new DigitalSignatureImpl();

        try {
            return digitalSignature.verify(record.toString()
                    , record.getDigitalSignature()
                    , KeyAccess.getPublicKey(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getKeyFileNameByUserFullName(String fullName){
        String filename = "";
        SymmCrypto symmCrypto = new SymmCrypto();
        try {
            filename = symmCrypto.encrypt(fullName, SecretCharsKeyGen.keygen())
                    .replaceAll("/","");
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return filename;
    }
}
