package com.n26.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.n26.endpoint.serialization.TransactionDeserializer;

import java.math.BigDecimal;

@JsonDeserialize(using = TransactionDeserializer.class)
public class Transaction {

    private BigDecimal amount;

    private Long timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static TransactionBuilder create(){
        return new TransactionBuilder();
    }

    public static final class TransactionBuilder {
        private Transaction transaction;

        private TransactionBuilder() {
            transaction = new Transaction();
        }

        public static TransactionBuilder aTransaction() {
            return new TransactionBuilder();
        }

        public TransactionBuilder amount(BigDecimal amount) {
            transaction.setAmount(amount);
            return this;
        }

        public TransactionBuilder timestamp(Long timestamp) {
            transaction.setTimestamp(timestamp);
            return this;
        }

        public Transaction build() {
            return transaction;
        }
    }
}
