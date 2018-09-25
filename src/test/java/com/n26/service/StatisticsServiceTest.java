package com.n26.service;

import com.n26.model.Statistics;
import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.persistence.mem.MemoryStorage;
import com.n26.service.impl.StatisticsServiceImpl;
import com.n26.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsServiceTest {

    private StatisticsServiceImpl target;
    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() throws Exception {
        MemoryStorage storage = new MemoryStorage();
        target = StatisticsServiceImpl.create().storage(storage).build();
        transactionService = TransactionServiceImpl.create().storage(storage).build();
    }

    @Test
    public void testGetStatisticsWithResults(){
        loadTransactions();
        Statistics statistics = target.get();
        assertNotNull(statistics);
    }

    private void loadTransactions() {
        IntStream.range(1, 201).forEach(i ->{
            transactionService.store(Transaction.create()
                    .timestamp(System.currentTimeMillis()-1000)
                    .amount(new BigDecimal(23.98*i))
                    .build());
        });
    }

}