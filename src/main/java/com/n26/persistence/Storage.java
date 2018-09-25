package com.n26.persistence;

import com.n26.model.Transaction;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface Storage {

    Transaction put(Transaction transaction);

    List<Transaction> getTransactionsLast(Integer period, TimeUnit seconds);
}
