package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private TransactionService target;

    @Before
    public void setUp() throws Exception {
        target = new TransactionService();
    }

    @Test
    public void testStoreTransactionOK(){
        Transaction transaction = Transaction.create().build();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.OK, result);
    }

    @Test
    public void testStoreTransactionOldTransaction(){
        Transaction transaction = Transaction.create()
                .amount(new BigDecimal(100.98))
                .timestamp(System.currentTimeMillis())
                .build();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.OLD_TRANSACTION_NOT_ALLOWED, result);
    }

}