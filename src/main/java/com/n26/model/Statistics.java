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

    public static Statistics empty() {
        return create()
                .avg("0.00")
                .max("0.00")
                .min("0.00")
                .sum("0.00")
                .count(0)
                .build();
    }

    public static Builder create() {
        return new Builder();
    }

    public static final class Builder {
        private Statistics target = new Statistics();


        public Builder sum(String sum) {
            this.target.sum = sum;
            return this;
        }

        public Builder avg(String avg) {
            this.target.avg = avg;
            return this;
        }

        public Builder max(String max) {
            this.target.max = max;
            return this;
        }

        public Builder min(String min) {
            this.target.min = min;
            return this;
        }

        public Builder count(Integer count) {
            this.target.count = count;
            return this;
        }

        public Statistics build() {
            return target;
        }
    }
}
