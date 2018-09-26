package com.n26.core.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class FormatterUtilTest {

    @Test
    public void formatBigDecimal() {
        assertEquals("332.45", FormatterUtil.format(new BigDecimal(332.4545545)));
        assertEquals("12.77", FormatterUtil.format(new BigDecimal(12.76767)));
        assertEquals("12.12", FormatterUtil.format(new BigDecimal(12.12)));
        assertEquals("32.00", FormatterUtil.format(new BigDecimal(32)));
        assertEquals("3.40", FormatterUtil.format(new BigDecimal(3.4)));
        assertEquals("32.80", FormatterUtil.format(new BigDecimal(32.80)));
    }

    @Test
    public void parse() {
        assertEquals(new Long(1526127152000l), FormatterUtil.parse("2018-05-12T12:12:32Z"));
        assertEquals(new Long(1537961794128l), FormatterUtil.parse("2018-09-26T11:36:34.128Z"));
        assertEquals(new Long(1537961811526l), FormatterUtil.parse("2018-09-26T11:36:51.526Z"));
        assertEquals(new Long(1537961798123l), FormatterUtil.parse("2018-09-26T11:36:38.123Z"));
    }

    @Test(expected = DateTimeParseException.class)
    public void parseError(){
        assertEquals(new Long(1526127152000l), FormatterUtil.parse("4/23/2018 11:32 PM"));
    }

    @Test(expected = DateTimeParseException.class)
    public void parseError2(){
        assertEquals(new Long(1526127152000l), FormatterUtil.parse("asdfasfdfasf"));
    }
}