package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;

/**
 * Transaction operations interface
 */
public interface TransactionService {

    /**
     * Store store result.
     *
     * @param transaction the transaction
     * @return the store result
     */
    StoreResult store(Transaction transaction);

    /**
     * Delete all.
     */
    void deleteAll();

}
