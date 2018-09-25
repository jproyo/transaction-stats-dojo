package com.n26.persistence.mem;

import com.n26.model.Transaction;
import com.n26.persistence.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorage implements Storage {

    private ConcurrentHashMap<Long, List<Transaction>> storage = new ConcurrentHashMap<>();

    @Override
    public Transaction put(Transaction transaction) {
        List<Transaction> transactions = storage.getOrDefault(transaction.getTimestamp(), new ArrayList<>());
        transactions.add(transaction);
        storage.putIfAbsent(transaction.getTimestamp(), transactions);
        storage.replace(transaction.getTimestamp(), transactions);
        return transaction;
    }
}
