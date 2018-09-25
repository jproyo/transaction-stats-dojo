package com.n26.service.impl;

import com.n26.core.util.FormatterUtil;
import com.n26.core.util.StatisticsCollector;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.StatisticsService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatisticsServiceImpl implements StatisticsService {

    private Storage storage;

    @Override
    public Statistics get() {
        List<Transaction> transactions = storage.getTransactionsLast(60, TimeUnit.SECONDS);
        StatisticsCollector collect = transactions.stream()
                .map(Transaction::getAmount)
                .collect(StatisticsCollector.create());
        return Statistics.create()
                .avg(FormatterUtil.format(collect.getAvg()))
                .max(FormatterUtil.format(collect.getMax()))
                .min(FormatterUtil.format(collect.getMin()))
                .sum(FormatterUtil.format(collect.getSum()))
                .count(collect.getCount())
                .build();
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public static StatisticsServiceImplBuilder create(){
        return new StatisticsServiceImplBuilder();
    }


    public static final class StatisticsServiceImplBuilder {
        private StatisticsServiceImpl statisticsServiceImpl;

        private StatisticsServiceImplBuilder() {
            statisticsServiceImpl = new StatisticsServiceImpl();
        }

        public static StatisticsServiceImplBuilder aStatisticsServiceImpl() {
            return new StatisticsServiceImplBuilder();
        }

        public StatisticsServiceImplBuilder storage(Storage storage) {
            statisticsServiceImpl.setStorage(storage);
            return this;
        }

        public StatisticsServiceImpl build() {
            return statisticsServiceImpl;
        }
    }
}
