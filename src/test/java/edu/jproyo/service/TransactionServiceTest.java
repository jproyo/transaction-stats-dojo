package edu.jproyo.service;

import edu.jproyo.config.Config;
import edu.jproyo.model.StoreResult;
import edu.jproyo.model.Transaction;
import edu.jproyo.persistence.mem.MemoryStorage;
import edu.jproyo.service.impl.TransactionServiceImpl;
import edu.jproyo.service.validator.TransactionValidator;
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
        assertEquals(StoreResult.OLD, result);
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
        assertEquals(StoreResult.FUTURE, result);
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