package com.moody.service;

import com.moody.blockchain.Drug;
import com.moody.blockchain.TransactionRecord;

public interface BlockchainService {
    boolean addTransactionRecord(String drugId, TransactionRecord record);
    boolean addNewDrug(Drug drug);
}
