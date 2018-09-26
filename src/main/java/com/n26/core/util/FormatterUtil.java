package com.n26.core.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FormatterUtil {


    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String format(BigDecimal number){
        return number.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static Long parse(String isoFormat){
        LocalDateTime deserialised = null;
        if (isoFormat.length() > 10 && isoFormat.charAt(10) == 'T') {
            deserialised = isoFormat.endsWith("Z") ? LocalDateTime.ofInstant(Instant.parse(isoFormat), ZoneOffset.UTC) : LocalDateTime.parse(isoFormat, DEFAULT_FORMATTER);
        } else {
            deserialised = LocalDateTime.parse(isoFormat, DEFAULT_FORMATTER);
        }
        return deserialised.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

}
