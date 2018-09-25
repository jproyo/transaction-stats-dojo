package com.n26.service;

import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private TransactionService target;

    @Before
    public void setUp() throws Exception {
        target = new TransactionService();
    }

    @Test
    public void testStoreTransactionSuccessful(){
        Transaction transaction = new Transaction();
        StoreResult result = target.store(transaction);
        assertNotNull(result);
        assertEquals(StoreResult.OK, result);
    }

}