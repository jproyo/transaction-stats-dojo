package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;

public interface TransactionService {
    StoreResult store(Transaction transaction);
}
