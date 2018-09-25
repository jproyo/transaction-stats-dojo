package com.n26.config;

import org.springframework.beans.factory.annotation.Value;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Config {

    @Value("${transaction.period.amount}")
    private Long amount = 60l;

    @Value("${transaction.period.unit}")
    private String unit = "SECONDS";

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public TemporalUnit unit(){
        return ChronoUnit.valueOf(unit.toUpperCase());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("amount=").append(amount);
        sb.append(", unit=").append(unit);
        sb.append('}');
        return sb.toString();
    }


}
