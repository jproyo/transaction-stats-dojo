package com.n26.model;

import java.math.BigDecimal;

public class Transaction {

    private BigDecimal amount;

    private Long timestamp;

    private Long now = System.currentTimeMillis();

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

    public boolean isOld(){
        long sixtySecsBefore = now - 60000;
        return getTimestamp() < sixtySecsBefore;
    }

    public boolean isFuture(){
        return getTimestamp() > now;
    }

    public boolean valid() {
        return !isOld() && !isFuture();
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
