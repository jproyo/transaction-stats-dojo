package com.n26.service.impl;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.persistence.Storage;
import com.n26.service.StatisticsService;

import java.util.DoubleSummaryStatistics;
import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {

    private Storage storage;

    @Override
    public Statistics get() {
        DoubleSummaryStatistics collect = storage.getAll()
                .values()
                .stream()
                .flatMap(List::stream)
                .map(Transaction::getAmount)
                .mapToDouble(t -> new Double(t.doubleValue()))
                .collect(
                        DoubleSummaryStatistics::new,
                        DoubleSummaryStatistics::accept,
                        DoubleSummaryStatistics::combine);
        return Statistics.create()
                .avg(Double.toString(collect.getAverage()))
                .max(Double.toString(collect.getMax()))
                .min(Double.toString(collect.getMin()))
                .sum(Double.toString(collect.getSum()))
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
