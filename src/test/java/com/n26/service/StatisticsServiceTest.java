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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsServiceTest {

    private StatisticsServiceImpl target;

    @Before
    public void setUp() throws Exception {
        target = StatisticsServiceImpl.create().storage(new MemoryStorage()).build();
    }

    @Test
    public void testGetStatisticsWithResults(){
        Statistics statistics = target.get();
        assertNotNull(statistics);
    }

}