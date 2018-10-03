package edu.jproyo.config;

import org.springframework.beans.factory.annotation.Value;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * Config class contains configuration about the period and unit transactions
 * are being taken as current transaction.
 */
public class Config {

    @Value("${transaction.period.amount}")
    private Long amount = 60l;

    @Value("${transaction.period.unit}")
    private String unit = "SECONDS";

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Unit temporal unit.
     *
     * @return the temporal unit
     */
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
