package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.mem.MemoryStorage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private TransactionService target;

    @Before
    public void setUp() throws Exception {
        target = TransactionService.create().storage(new MemoryStorage()).build();
    }

    @Test
    public void testStoreTransactionOK(){
        Transaction transaction = Transaction.create()
                .amount(new BigDecimal(100.98))
                .timestamp(System.currentTimeMillis()-1000)
                .build();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.OK, result);
    }

    @Test
    public void testStoreTransactionOldTransaction(){
        long timestamp = System.currentTimeMillis() - 61000;
        Transaction transaction = Transaction.create()
                .amount(new BigDecimal(100.98))
                .timestamp(timestamp)
                .build();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.OLD_TRANSACTION_NOT_ALLOWED, result);
    }


    @Test
    public void testStoreTransactionFutureTransaction(){
        long timestamp = System.currentTimeMillis() + 1000;
        Transaction transaction = Transaction.create()
                .amount(new BigDecimal(100.98))
                .timestamp(timestamp)
                .build();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.FUTURE_TRANSACTION_NOT_ALLOWED, result);
    }

    @Test
    public void testStoreTransactionNullTransaction(){
        StoreResult result = target.store(null);
        assertNotNull(result);
        assertEquals(StoreResult.NO_CONTENT, result);
    }

    @Test
    public void testStoreTransactionTransactionWrongAmount(){
        StoreResult result = target.store(Transaction.create().build());
        assertNotNull(result);
        assertEquals(StoreResult.NO_CONTENT, result);
    }

}