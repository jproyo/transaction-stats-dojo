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
import java.util.Arrays;
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

    @Test
    public void testGetStatisticsWithoutOldResults(){
        loadTransactionsWithoutOldResults();
        Statistics statistics = target.get();
        assertNotNull(statistics);
        Statistics statsExpected = Statistics.create()
                .sum("4479918.82")
                .avg("639988.40")
                .min("14.65")
                .max("2232421.12")
                .count(7l)
                .build();
        assertEquals(statsExpected, statistics);
    }

    private void loadTransactionsWithoutOldResults() {
        IntStream.range(1, 11).forEach(i ->{
            transactionService.store(Transaction.create()
                    .timestamp(System.currentTimeMillis()-61000)
                    .amount(new BigDecimal(21.32*i))
                    .build());
        });

        Arrays.asList(
                 Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(21.38))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(14.6546456))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(31.578))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(1223221.35558))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(757547.354358))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(266661.378888))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(2232421.12231238))
                        .build()
        ).stream().forEach(transactionService::store);
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