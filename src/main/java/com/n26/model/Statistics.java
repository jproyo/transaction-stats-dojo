package com.n26.model;

import java.util.Objects;

public class Statistics {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private Long count;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(avg, that.avg) &&
                Objects.equals(max, that.max) &&
                Objects.equals(min, that.min) &&
                Objects.equals(count, that.count);
    }

    public static StatisticsBuilder create(){
        return new StatisticsBuilder();
    }

    public static Statistics empty() {
        return create()
                .avg("0.00")
                .max("0.00")
                .min("0.00")
                .sum("0.00")
                .count(0l)
                .build();
    }


    public static final class StatisticsBuilder {
        private Statistics statistics;

        private StatisticsBuilder() {
            statistics = new Statistics();
        }

        public static StatisticsBuilder aStatistics() {
            return new StatisticsBuilder();
        }

        public StatisticsBuilder sum(String sum) {
            statistics.setSum(sum);
            return this;
        }

        public StatisticsBuilder avg(String avg) {
            statistics.setAvg(avg);
            return this;
        }

        public StatisticsBuilder max(String max) {
            statistics.setMax(max);
            return this;
        }

        public StatisticsBuilder min(String min) {
            statistics.setMin(min);
            return this;
        }

        public StatisticsBuilder count(Long count) {
            statistics.setCount(count);
            return this;
        }

        public Statistics build() {
            return statistics;
        }
    }
}
