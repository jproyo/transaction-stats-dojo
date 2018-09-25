package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;

public class TransactionService {


    public StoreResult store(Transaction transaction) {
        return StoreResult.OK;
    }
}
