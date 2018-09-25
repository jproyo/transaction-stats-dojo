package com.n26.persistence.mem;

import com.n26.model.Transaction;
import com.n26.persistence.Storage;

public class MemoryStorage implements Storage {
    @Override
    public Transaction put(Transaction transaction) {
        return transaction;
    }
}
