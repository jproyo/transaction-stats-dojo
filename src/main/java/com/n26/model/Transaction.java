package com.n26.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.n26.endpoint.serialization.TransactionDeserializer;

import java.math.BigDecimal;

/**
 * Transaction
 */
@JsonDeserialize(using = TransactionDeserializer.class)
public class Transaction {

    private BigDecimal amount;

    private Long timestamp;

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("amount=").append(amount);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Create transaction builder.
     *
     * @return the transaction builder
     */
    public static TransactionBuilder create(){
        return new TransactionBuilder();
    }

    /**
     * The type Transaction builder.
     */
    public static final class TransactionBuilder {
        private Transaction transaction;

        private TransactionBuilder() {
            transaction = new Transaction();
        }

        /**
         * A transaction transaction builder.
         *
         * @return the transaction builder
         */
        public static TransactionBuilder aTransaction() {
            return new TransactionBuilder();
        }

        /**
         * Amount transaction builder.
         *
         * @param amount the amount
         * @return the transaction builder
         */
        public TransactionBuilder amount(BigDecimal amount) {
            transaction.setAmount(amount);
            return this;
        }

        /**
         * Timestamp transaction builder.
         *
         * @param timestamp the timestamp
         * @return the transaction builder
         */
        public TransactionBuilder timestamp(Long timestamp) {
            transaction.setTimestamp(timestamp);
            return this;
        }

        /**
         * Build transaction.
         *
         * @return the transaction
         */
        public Transaction build() {
            return transaction;
        }
    }
}
