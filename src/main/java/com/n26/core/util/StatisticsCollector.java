package com.n26.core.util;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class StatisticsCollector implements Consumer<BigDecimal> {

    private BigDecimal sum = BigDecimal.ZERO, min, max;
    private long count;

    public void accept(BigDecimal t) {
        if(count == 0) {
            Objects.requireNonNull(t);
            count = 1;
            sum = t;
            min = t;
            max = t;
        }
        else {
            sum = sum.add(t);
            if(min.compareTo(t) > 0) min = t;
            if(max.compareTo(t) < 0) max = t;
            count++;
        }
    }
    public StatisticsCollector merge(StatisticsCollector s) {
        if(s.count > 0) {
            if(count == 0) {
                count = s.count;
                sum = s.sum;
                min = s.min;
                max = s.max;
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

    public long getCount() {
        return count;
    }

    public BigDecimal getSum() {
      return sum;
    }

    public BigDecimal getAvg() {
      return count < 2 ? sum : sum.divide(BigDecimal.valueOf(count));
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public static Collector<BigDecimal, ?, StatisticsCollector> create() {
        return Collector.of(StatisticsCollector::new,
                StatisticsCollector::accept,
                StatisticsCollector::merge);
    }

}