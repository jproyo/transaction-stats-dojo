package com.n26.persistence;

import com.n26.model.Transaction;

import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

public interface Storage {

    Transaction put(Transaction transaction);

    List<Transaction> getTransactionsLast(Long amount, TemporalUnit unit);

    void deleteAll();

    Map<Long, List<Transaction>> getAll();
}
