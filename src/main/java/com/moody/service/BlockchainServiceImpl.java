package com.moody.service;

import com.moody.authentication.UserBank;
import com.moody.blockchain.*;
import com.moody.crypto.SymmCrypto;
import com.moody.digitalSignature.DigitalSignature;
import com.moody.digitalSignature.DigitalSignatureImpl;
import com.moody.keygen.KeyAccess;
import com.moody.keygen.SecretCharsKeyGen;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Blockchain.mining(count+1, target.getHeader().getCurrentHash());
        Blockchain.persist();
        Blockchain.distribute();
        return true;
    }

    @Override
    public boolean addNewDrug(Drug drug) {
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
    public List<TransactionRecord> getTransactionRecordData(String drugId) {
        Optional<Block> block = Blockchain.findBlock(drugId);
        if(block.isEmpty()){
            return null;
        }

        return block.get().getTranx().getTranxLst().stream()
                .sorted(Comparator.comparing(TransactionRecord::getDateTime))
                .collect(Collectors.toList());
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
