package com.n26.service.impl;

import com.n26.config.Config;
import com.n26.core.util.FormatterUtil;
import com.n26.core.util.StatisticsCollector;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private Storage storage;

    @Autowired
    private Config config;

    @Override
    public Statistics get() {
        List<Transaction> transactions = storage.getTransactionsLast(config.getAmount(), config.unit());
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

    public void setConfig(Config config) {
        this.config = config;
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

        public StatisticsServiceImplBuilder config(Config config) {
            statisticsServiceImpl.setConfig(config);
            return this;
        }


        public StatisticsServiceImpl build() {
            return statisticsServiceImpl;
        }
    }
}
