package com.n26.persistence;

import com.n26.model.Transaction;

import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

/**
 * Storage Interface that represents operations that can be done on real storage
 * implementation
 */
public interface Storage {

    /**
     * Put transaction.
     *
     * @param transaction the transaction
     * @return the transaction
     */
    Transaction put(Transaction transaction);

    /**
     * Gets transactions last.
     *
     * @param amount the amount
     * @param unit   the unit
     * @return the transactions last
     */
    List<Transaction> getTransactionsLast(Long amount, TemporalUnit unit);

    /**
     * Delete all.
     */
    void deleteAll();

    /**
     * Gets all.
     *
     * @return the all
     */
    Map<Long, List<Transaction>> getAll();
}
