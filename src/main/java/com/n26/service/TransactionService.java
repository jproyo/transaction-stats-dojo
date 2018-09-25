package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;

import java.util.Optional;

public class TransactionService {

    private Storage storage;

    public StoreResult store(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .filter(t -> t.valid())
                .map(storage::put)
                .map(t -> StoreResult.OK)
                .orElse(resultFromTransaction(transaction));
    }

    public StoreResult resultFromTransaction(Transaction transaction){
        if(transaction == null || transaction.noContent()) return StoreResult.NO_CONTENT;
        if(transaction.isOld()) return StoreResult.OLD_TRANSACTION_NOT_ALLOWED;
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
        private TransactionService transactionService;

        private TransactionServiceBuilder() {
            transactionService = new TransactionService();
        }

        public static TransactionServiceBuilder aTransactionService() {
            return new TransactionServiceBuilder();
        }

        public TransactionServiceBuilder storage(Storage storage) {
            transactionService.setStorage(storage);
            return this;
        }

        public TransactionService build() {
            return transactionService;
        }
    }
}
