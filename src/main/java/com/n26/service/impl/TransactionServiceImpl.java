package com.n26.service.impl;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.TransactionService;
import com.n26.service.validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Storage storage;

    @Autowired
    private TransactionValidator validator;


    @Override
    public StoreResult store(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .filter(validator::valid)
                .map(storage::put)
                .map(t -> StoreResult.OK)
                .orElse(resultFromTransaction(transaction));
    }

    @Override
    public void deleteAll() {
        storage.deleteAll();
    }

    public StoreResult resultFromTransaction(Transaction transaction){
        if(validator.noContent(transaction)) {
            return StoreResult.NO_CONTENT;
        } else {
            return StoreResult.INVALID_TRANSACTION;
        }
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setValidator(TransactionValidator validator) {
        this.validator = validator;
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

        public TransactionServiceBuilder validator(TransactionValidator validator) {
            transactionService.setValidator(validator);
            return this;
        }

        public TransactionServiceImpl build() {
            return transactionService;
        }
    }
}
