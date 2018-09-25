package com.n26.persistence;

import com.n26.model.Transaction;

import java.util.List;
import java.util.Map;

public interface Storage {

    Transaction put(Transaction transaction);

    Map<Long, List<Transaction>> getAll();
}
