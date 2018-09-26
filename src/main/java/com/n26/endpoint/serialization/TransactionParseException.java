package com.n26.endpoint.serialization;

public class TransactionParseException extends RuntimeException{
    public TransactionParseException() {
    }

    public TransactionParseException(String message) {
        super(message);
    }

    public TransactionParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionParseException(Throwable cause) {
        super(cause);
    }
}
