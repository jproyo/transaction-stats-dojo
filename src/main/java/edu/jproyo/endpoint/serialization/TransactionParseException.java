package edu.jproyo.endpoint.serialization;

/**
 * Parse Exception to be handle by Controller
 */
public class TransactionParseException extends RuntimeException{
    /**
     * Instantiates a new Transaction parse exception.
     */
    public TransactionParseException() {
    }

    /**
     * Instantiates a new Transaction parse exception.
     *
     * @param message the message
     */
    public TransactionParseException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Transaction parse exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TransactionParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Transaction parse exception.
     *
     * @param cause the cause
     */
    public TransactionParseException(Throwable cause) {
        super(cause);
    }
}
