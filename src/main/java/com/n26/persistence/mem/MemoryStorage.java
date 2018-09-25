package com.n26.persistence.mem;

import com.n26.model.Transaction;
import com.n26.persistence.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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

    @Override
    public List<Transaction> getTransactionsLast(Integer period, TimeUnit seconds) {
        long now = System.currentTimeMillis();
        long sixtySeconds = now - 60000;
        return LongStream.rangeClosed(sixtySeconds, now)
                .mapToObj(time -> storage.getOrDefault(time, new ArrayList<>()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
