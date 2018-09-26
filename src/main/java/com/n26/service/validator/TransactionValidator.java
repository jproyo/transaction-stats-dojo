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

    public ValidatorResult validate(Transaction transaction){
        if(noContent(transaction)) return ValidatorResult.INVALID;
        Instant now = Instant.now(Clock.systemUTC());
        Instant past = now.minus(config.getAmount(), config.unit());
        if(isOld(transaction, past)) return ValidatorResult.OLD;
        if(isFutre(transaction, now)) return ValidatorResult.FUTURE;
        return ValidatorResult.VALID;
    }

    public boolean noContent(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .map(t -> t.getAmount() == null || t.getTimestamp() == null)
                .orElse(true);
    }

    public boolean isOld(Transaction transaction, Instant past) {
        return transaction.getTimestamp() < past.toEpochMilli();
    }

    public boolean isFutre(Transaction transaction, Instant now) {
        return transaction.getTimestamp() > now.toEpochMilli();
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
