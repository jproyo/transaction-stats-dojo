package edu.jproyo.service.validator;

import edu.jproyo.config.Config;
import edu.jproyo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

/**
 * Validator which understand if the transaction is valid or not
 */
public class TransactionValidator implements Validator<Transaction>{

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

    /**
     * No content boolean.
     *
     * @param transaction the transaction
     * @return the boolean
     */
    public boolean noContent(Transaction transaction) {
        return Optional.ofNullable(transaction)
                .map(t -> t.getAmount() == null || t.getTimestamp() == null)
                .orElse(true);
    }

    /**
     * Is old boolean.
     *
     * @param transaction the transaction
     * @param past        the past
     * @return the boolean
     */
    public boolean isOld(Transaction transaction, Instant past) {
        return transaction.getTimestamp() < past.toEpochMilli();
    }

    /**
     * Is futre boolean.
     *
     * @param transaction the transaction
     * @param now         the now
     * @return the boolean
     */
    public boolean isFutre(Transaction transaction, Instant now) {
        return transaction.getTimestamp() > now.toEpochMilli();
    }

    /**
     * Sets config.
     *
     * @param config the config
     */
    public void setConfig(Config config) {
        this.config = config;
    }


    /**
     * Create transaction validator builder.
     *
     * @return the transaction validator builder
     */
    public static TransactionValidatorBuilder create() {
        return new TransactionValidatorBuilder();
    }

    /**
     * The type Transaction validator builder.
     */
    public static final class TransactionValidatorBuilder {
        private TransactionValidator transactionValidator;

        private TransactionValidatorBuilder() {
            transactionValidator = new TransactionValidator();
        }

        /**
         * Config transaction validator builder.
         *
         * @param config the config
         * @return the transaction validator builder
         */
        public TransactionValidatorBuilder config(Config config) {
            transactionValidator.setConfig(config);
            return this;
        }

        /**
         * Build transaction validator.
         *
         * @return the transaction validator
         */
        public TransactionValidator build() {
            return transactionValidator;
        }
    }
}
