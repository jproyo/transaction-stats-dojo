package com.n26.model;

public class Statistics {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
                .count(0)
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

        public StatisticsBuilder count(Integer count) {
            statistics.setCount(count);
            return this;
        }

        public Statistics build() {
            return statistics;
        }
    }
}
