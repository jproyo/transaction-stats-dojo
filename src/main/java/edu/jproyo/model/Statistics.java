package edu.jproyo.model;

import java.util.Objects;

/**
 * Statistics Product Type
 */
public class Statistics {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private Long count;

    /**
     * Gets sum.
     *
     * @return the sum
     */
    public String getSum() {
        return sum;
    }

    /**
     * Sets sum.
     *
     * @param sum the sum
     */
    public void setSum(String sum) {
        this.sum = sum;
    }

    /**
     * Gets avg.
     *
     * @return the avg
     */
    public String getAvg() {
        return avg;
    }

    /**
     * Sets avg.
     *
     * @param avg the avg
     */
    public void setAvg(String avg) {
        this.avg = avg;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public String getMax() {
        return max;
    }

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(String max) {
        this.max = max;
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public String getMin() {
        return min;
    }

    /**
     * Sets min.
     *
     * @param min the min
     */
    public void setMin(String min) {
        this.min = min;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Statistics{");
        sb.append("sum='").append(sum).append('\'');
        sb.append(", avg='").append(avg).append('\'');
        sb.append(", max='").append(max).append('\'');
        sb.append(", min='").append(min).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Create statistics builder.
     *
     * @return the statistics builder
     */
    public static StatisticsBuilder create(){
        return new StatisticsBuilder();
    }

    /**
     * Empty statistics.
     *
     * @return the statistics
     */
    public static Statistics empty() {
        return create()
                .avg("0.00")
                .max("0.00")
                .min("0.00")
                .sum("0.00")
                .count(0l)
                .build();
    }


    /**
     * The type Statistics builder.
     */
    public static final class StatisticsBuilder {
        private Statistics statistics;

        private StatisticsBuilder() {
            statistics = new Statistics();
        }

        /**
         * A statistics statistics builder.
         *
         * @return the statistics builder
         */
        public static StatisticsBuilder aStatistics() {
            return new StatisticsBuilder();
        }

        /**
         * Sum statistics builder.
         *
         * @param sum the sum
         * @return the statistics builder
         */
        public StatisticsBuilder sum(String sum) {
            statistics.setSum(sum);
            return this;
        }

        /**
         * Avg statistics builder.
         *
         * @param avg the avg
         * @return the statistics builder
         */
        public StatisticsBuilder avg(String avg) {
            statistics.setAvg(avg);
            return this;
        }

        /**
         * Max statistics builder.
         *
         * @param max the max
         * @return the statistics builder
         */
        public StatisticsBuilder max(String max) {
            statistics.setMax(max);
            return this;
        }

        /**
         * Min statistics builder.
         *
         * @param min the min
         * @return the statistics builder
         */
        public StatisticsBuilder min(String min) {
            statistics.setMin(min);
            return this;
        }

        /**
         * Count statistics builder.
         *
         * @param count the count
         * @return the statistics builder
         */
        public StatisticsBuilder count(Long count) {
            statistics.setCount(count);
            return this;
        }

        /**
         * Build statistics.
         *
         * @return the statistics
         */
        public Statistics build() {
            return statistics;
        }
    }
}
