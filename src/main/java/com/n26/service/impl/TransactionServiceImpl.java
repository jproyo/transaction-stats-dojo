package com.n26.service.impl;

import com.n26.core.util.Either;
import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.TransactionService;
import com.n26.service.validator.Validator;
import com.n26.service.validator.ValidatorResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Storage storage;

    @Autowired
    private Validator validator;


    @Override
    public StoreResult store(Transaction transaction) {
        ValidatorResult validate = validator.validate(transaction);
        Either<ValidatorResult, Transaction> projection = null;
        if(ValidatorResult.VALID.equals(validate)){
            projection = Either.right(transaction);
        }else{
            projection = Either.left(validate);
        }
        return projection.map(mapErrors(), storeTransaction());
    }

    private Function<ValidatorResult, StoreResult> mapErrors(){
        return validator -> {
            switch (validator){
                case INVALID: return StoreResult.NO_CONTENT;
                default: return StoreResult.valueOf(validator.name());
            }
        };
    }

    private Function<Transaction, StoreResult> storeTransaction(){
        return transaction -> {
            storage.put(transaction);
            return StoreResult.OK;
        };
    }

    @Override
    public void deleteAll() {
        storage.deleteAll();
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setValidator(Validator validator) {
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

        public TransactionServiceBuilder validator(Validator validator) {
            transactionService.setValidator(validator);
            return this;
        }

        public TransactionServiceImpl build() {
            return transactionService;
        }
    }
}
