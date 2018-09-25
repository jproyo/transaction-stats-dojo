package com.n26.service.validator;

import com.n26.config.Config;
import com.n26.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

public class TransactionValidator implements Validator{

    @Autowired
    private Config config;

    public boolean valid(Transaction transaction){
        return !noContent(transaction) && validTime(transaction);
    }

    public boolean noContent(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .map(t -> t.getAmount() == null || t.getTimestamp() == null)
                .orElse(true);
    }

    public boolean validTime(Transaction transaction) {
        Instant now = Instant.now(Clock.systemUTC());
        Instant past = now.minus(config.getAmount(), config.unit());
        return Optional.ofNullable(transaction)
                .map(t -> t.getTimestamp() >= past.toEpochMilli()
                            && t.getTimestamp() <= now.toEpochMilli()
                )
                .orElse(true);
    }

    public void setConfig(Config config) {
        this.config = config;
    }


    public static TransactionValidatorBuilder create() {
        return new TransactionValidatorBuilder();
    }

    public static final class TransactionValidatorBuilder {
        private TransactionValidator transactionValidator;

        private TransactionValidatorBuilder() {
            transactionValidator = new TransactionValidator();
        }

        public TransactionValidatorBuilder config(Config config) {
            transactionValidator.setConfig(config);
            return this;
        }

        public TransactionValidator build() {
            return transactionValidator;
        }
    }
}
