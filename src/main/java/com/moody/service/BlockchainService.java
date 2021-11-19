package com.moody.service;

import com.moody.blockchain.Block;
import com.moody.blockchain.Drug;
import com.moody.blockchain.TransactionRecord;
import com.moody.blockchain.TransactionType;

import java.util.List;

public interface BlockchainService {
    boolean addTransactionRecord(String drugId, TransactionType type);
    boolean addNewDrug(Drug drug);
    List<TransactionRecord> getTransactionRecordData(String drugId);
}
