package com.moody.service;

import com.moody.blockchain.*;

import java.util.Objects;

public class BlockchainServiceImpl implements BlockchainService{

    @Override
    public boolean addTransactionRecord(String drugId, TransactionRecord record) {
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
                "Tam Kai Jun",
                "Moody Co",
                "Bukit Jalil",
                BusinessType.SUPPLIER));
        newBlock.setTranx(newTranx);
        Blockchain.nextBlock( newBlock);
        Blockchain.distribute();
        return true;
    }
}
