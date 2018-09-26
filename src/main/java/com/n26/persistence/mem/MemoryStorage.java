package com.n26.persistence.mem;

import com.n26.model.Transaction;
import com.n26.persistence.Storage;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Memory Collection Representation of the Storage
 * Basically Transactions are being stored in a Map in the following way:
 * - Key: Long timestamp
 * - Value: List of Transactions in that timestamp
 *
 * In this implementation we can have constant-time for add, remove and get operations
 * fulfilling requirements.
 *
 */
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
    public List<Transaction> getTransactionsLast(Long amount, TemporalUnit unit) {
        Instant now = Instant.now(Clock.systemUTC());
        Instant past = now.minus(amount, unit);
        return LongStream.rangeClosed(past.toEpochMilli(), now.toEpochMilli())
                .mapToObj(time -> storage.getOrDefault(time, new ArrayList<>()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public Map<Long, List<Transaction>> getAll() {
        return storage;
    }
}
