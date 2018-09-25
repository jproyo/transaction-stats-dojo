package com.n26.service.impl;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.TransactionService;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private Storage storage;

    private Long amount = 60l;
    private TemporalUnit unit = ChronoUnit.valueOf("seconds".toUpperCase());

    @Override
    public StoreResult store(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .filter(Transaction::valid)
                .map(storage::put)
                .map(t -> StoreResult.OK)
                .orElse(resultFromTransaction(transaction));
    }

    @Override
    public void deleteAll() {
        storage.deleteAll();
    }

    public StoreResult resultFromTransaction(Transaction transaction){
        if(transaction == null || transaction.noContent()) return StoreResult.NO_CONTENT;
        if(transaction.isOld(amount, unit)) return StoreResult.OLD_TRANSACTION_NOT_ALLOWED;
        else if(transaction.isFuture()) return StoreResult.FUTURE_TRANSACTION_NOT_ALLOWED;
        return StoreResult.OK;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public static TransactionServiceBuilder create(){
        return new TransactionServiceBuilder();
    }

    public static final class TransactionServiceBuilder {
        private TransactionServiceImpl transactionService;

        private TransactionServiceBuilder() {
            transactionService = new TransactionServiceImpl();
        }

        public static TransactionServiceBuilder aTransactionService() {
            return new TransactionServiceBuilder();
        }

        public TransactionServiceBuilder storage(Storage storage) {
            transactionService.setStorage(storage);
            return this;
        }

        public TransactionServiceImpl build() {
            return transactionService;
        }
    }
}
