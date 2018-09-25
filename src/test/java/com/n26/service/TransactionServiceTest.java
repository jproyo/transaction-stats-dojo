package com.n26.service;

import com.n26.config.Config;
import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.mem.MemoryStorage;
import com.n26.service.impl.TransactionServiceImpl;
import com.n26.service.validator.TransactionValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private TransactionServiceImpl target;

    private MemoryStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new MemoryStorage();
        target = TransactionServiceImpl.create()
                .storage(storage)
                .validator(TransactionValidator.create().config(new Config()).build())
                .build();
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
        assertEquals(StoreResult.INVALID_TRANSACTION, result);
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
        assertEquals(StoreResult.INVALID_TRANSACTION, result);
    }

    @Test
    public void testStoreTransactionNullTransaction(){
        StoreResult result = target.store(null);
        assertNotNull(result);
        assertEquals(StoreResult.NO_CONTENT, result);
    }

    @Test
    public void testStoreTransactionTransactionWrongAmount(){
        StoreResult result = target.store(Transaction.create().timestamp(System.currentTimeMillis()).build());
        assertNotNull(result);
        assertEquals(StoreResult.NO_CONTENT, result);
    }

    @Test
    public void testStoreTransactionTransactionWrongTime(){
        StoreResult result = target.store(Transaction.create().amount(new BigDecimal(0.98)).build());
        assertNotNull(result);
        assertEquals(StoreResult.NO_CONTENT, result);
    }


    @Test
    public void testDeleteAll(){
        LoadDataUtils.loadTransactions(target);
        target.deleteAll();
        assertTrue(storage.getAll().isEmpty());
    }


}