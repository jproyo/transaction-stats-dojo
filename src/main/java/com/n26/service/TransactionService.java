package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;

import java.util.Optional;
import java.util.function.Function;

public class TransactionService {

    private Storage storage;

    public StoreResult store(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .filter(Transaction::valid)
                .map(storage::put)
                .map(t -> StoreResult.OK)
                .orElse(resultFromTransaction(transaction));
    }

    public StoreResult resultFromTransaction(Transaction transaction){
        if(transaction.isOld()) return StoreResult.OLD_TRANSACTION_NOT_ALLOWED;
        else if(transaction.isFuture()) return StoreResult.FUTURE_TRANSACTION_NOT_ALLOWED;
        return StoreResult.OK;
    }
}
