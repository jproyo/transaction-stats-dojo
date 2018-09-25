package com.n26.service.impl;

import com.n26.model.Statistics;
import com.n26.persistence.Storage;
import com.n26.service.StatisticsService;

public class StatisticsServiceImpl implements StatisticsService {

    private Storage storage;

    @Override
    public Statistics get() {
        return null;
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
