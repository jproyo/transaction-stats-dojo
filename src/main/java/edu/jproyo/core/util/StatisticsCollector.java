package edu.jproyo.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;
import java.util.stream.Collector;

/**
 * Statistics collector based on BigDecimal amounts
 */
public class StatisticsCollector implements Consumer<BigDecimal> {

    private BigDecimal sum = BigDecimal.ZERO;

    private BigDecimal min = BigDecimal.ZERO;

    private BigDecimal max = BigDecimal.ZERO;

    private long count = 0;

    public void accept(BigDecimal t) {
        if(count == 0) {
            sum = min = max = t;
        } else {
            sum = sum.add(t);
            if(min.compareTo(t) > 0) min = t;
            if(max.compareTo(t) < 0) max = t;
        }
        count++;
    }

    /**
     * Merge statistics collector.
     *
     * @param s the s
     * @return the statistics collector
     */
    public StatisticsCollector merge(StatisticsCollector s) {
        if(s.count > 0) {
            if(count == 0) {
                count = s.count;
                sum = max = min = s.sum;
            }
            else {
                sum = sum.add(s.sum);
                if(min.compareTo(s.min) > 0) min = s.min;
                if(max.compareTo(s.max) < 0) max = s.max;
                count += s.count;
            }
        }
        return this;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * Gets sum.
     *
     * @return the sum
     */
    public BigDecimal getSum() {
      return sum;
    }

    /**
     * Gets avg.
     *
     * @return the avg
     */
    public BigDecimal getAvg() {
      return count < 2 ? sum : sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public BigDecimal getMin() {
        return min;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public BigDecimal getMax() {
        return max;
    }

    /**
     * Create collector.
     *
     * @return the collector
     */
    public static Collector<BigDecimal, ?, StatisticsCollector> create() {
        return Collector.of(StatisticsCollector::new,
                StatisticsCollector::accept,
                StatisticsCollector::merge);
    }

}