package com.n26.service.impl;

import com.n26.core.cat.Either;
import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.TransactionService;
import com.n26.service.validator.Validator;
import com.n26.service.validator.ValidatorResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

/**
 * Transaction default implementation service
 */
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Storage storage;

    @Autowired
    private Validator<Transaction> validator;


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

    /**
     * Sets storage.
     *
     * @param storage the storage
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Sets validator.
     *
     * @param validator the validator
     */
    public void setValidator(Validator<Transaction> validator) {
        this.validator = validator;
    }

    /**
     * Create transaction service builder.
     *
     * @return the transaction service builder
     */
    public static TransactionServiceBuilder create(){
        return new TransactionServiceBuilder();
    }

    /**
     * The type Transaction service builder.
     */
    public static final class TransactionServiceBuilder {
        private TransactionServiceImpl transactionService;

        private TransactionServiceBuilder() {
            transactionService = new TransactionServiceImpl();
        }

        /**
         * A transaction service transaction service builder.
         *
         * @return the transaction service builder
         */
        public static TransactionServiceBuilder aTransactionService() {
            return new TransactionServiceBuilder();
        }

        /**
         * Storage transaction service builder.
         *
         * @param storage the storage
         * @return the transaction service builder
         */
        public TransactionServiceBuilder storage(Storage storage) {
            transactionService.setStorage(storage);
            return this;
        }

        /**
         * Validator transaction service builder.
         *
         * @param validator the validator
         * @return the transaction service builder
         */
        public TransactionServiceBuilder validator(Validator<Transaction> validator) {
            transactionService.setValidator(validator);
            return this;
        }

        /**
         * Build transaction service.
         *
         * @return the transaction service
         */
        public TransactionServiceImpl build() {
            return transactionService;
        }
    }
}
