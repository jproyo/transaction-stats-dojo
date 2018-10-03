package edu.jproyo.service;

import edu.jproyo.model.StoreResult;
import edu.jproyo.model.Transaction;

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
