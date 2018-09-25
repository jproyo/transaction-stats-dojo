package com.n26.persistence;

import com.n26.model.Transaction;

public interface Storage {

    Transaction put(Transaction transaction);
}
