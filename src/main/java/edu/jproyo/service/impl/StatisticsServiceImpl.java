package edu.jproyo.service.impl;

import edu.jproyo.config.Config;
import edu.jproyo.core.util.FormatterUtil;
import edu.jproyo.core.util.StatisticsCollector;
import edu.jproyo.model.Statistics;
import edu.jproyo.model.Transaction;
import edu.jproyo.persistence.Storage;
import edu.jproyo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Default Statistics operation implementation
 */
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

    /**
     * Sets storage.
     *
     * @param storage the storage
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Sets config.
     *
     * @param config the config
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * Create statistics service impl builder.
     *
     * @return the statistics service impl builder
     */
    public static StatisticsServiceImplBuilder create(){
        return new StatisticsServiceImplBuilder();
    }


    /**
     * The type Statistics service impl builder.
     */
    public static final class StatisticsServiceImplBuilder {
        private StatisticsServiceImpl statisticsServiceImpl;

        private StatisticsServiceImplBuilder() {
            statisticsServiceImpl = new StatisticsServiceImpl();
        }

        /**
         * A statistics service statistics service impl builder.
         *
         * @return the statistics service impl builder
         */
        public static StatisticsServiceImplBuilder aStatisticsServiceImpl() {
            return new StatisticsServiceImplBuilder();
        }

        /**
         * Storage statistics service impl builder.
         *
         * @param storage the storage
         * @return the statistics service impl builder
         */
        public StatisticsServiceImplBuilder storage(Storage storage) {
            statisticsServiceImpl.setStorage(storage);
            return this;
        }

        /**
         * Config statistics service impl builder.
         *
         * @param config the config
         * @return the statistics service impl builder
         */
        public StatisticsServiceImplBuilder config(Config config) {
            statisticsServiceImpl.setConfig(config);
            return this;
        }


        /**
         * Build statistics service.
         *
         * @return the statistics service
         */
        public StatisticsServiceImpl build() {
            return statisticsServiceImpl;
        }
    }
}
