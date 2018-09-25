package com.n26.service;

import org.junit.Before;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private TransactionService target;

    @Before
    public void setUp() throws Exception {
        target = new TransactionService();
    }

    public void testStoreTransactionSuccessful(){
        Transaction transaction = new Transaction();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.OK, result);
    }

}