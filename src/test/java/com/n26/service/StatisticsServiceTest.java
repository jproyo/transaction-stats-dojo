package com.n26.service;

import com.n26.config.Config;
import com.n26.core.util.FormatterUtil;
import com.n26.model.Statistics;
import com.n26.persistence.mem.MemoryStorage;
import com.n26.service.impl.StatisticsServiceImpl;
import com.n26.service.impl.TransactionServiceImpl;
import com.n26.service.validator.TransactionValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsServiceTest {

    private StatisticsServiceImpl target;

    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() throws Exception {
        MemoryStorage storage = new MemoryStorage();
        Config config = new Config();
        TransactionValidator validator = TransactionValidator.create().config(config).build();
        target = StatisticsServiceImpl.create()
                .storage(storage)
                .config(config)
                .build();
        transactionService = TransactionServiceImpl.create()
                .storage(storage)
                .validator(validator)
                .build();
    }

    @Test
    public void testGetStatisticsWithResults(){
        LoadDataUtils.loadTransactions(transactionService);
        Statistics statistics = target.get();
        assertNotNull(statistics);
    }

    @Test
    public void testGetStatisticsWithoutOldResults(){
        LoadDataUtils.loadTransactionsWithoutOldResults(transactionService);
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


    @Test
    public void testGetStatisticsEmpty(){
        Statistics statistics = target.get();
        assertNotNull(statistics);
        assertEquals(Statistics.empty(), statistics);
    }

    @Test
    public void testGetStatisticsIntegrationTestFailing(){
        LoadDataUtils.loadTransaction(transactionService, 5,System.currentTimeMillis() - 1000);
        LoadDataUtils.loadTransaction(transactionService, 3,System.currentTimeMillis() - 1000);
        LoadDataUtils.loadTransaction(transactionService, 3,System.currentTimeMillis() - 1000);
        Statistics statistics = target.get();
        assertNotNull(statistics);
        assertEquals("3.67", statistics.getAvg());
        assertEquals("5.00", statistics.getMax());
        assertEquals("3.00", statistics.getMin());
        assertEquals("11.00", statistics.getSum());
        assertEquals(new Long(3), statistics.getCount());
    }
}