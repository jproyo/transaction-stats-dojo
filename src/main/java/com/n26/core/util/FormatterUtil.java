package com.n26.core.util;

import java.math.BigDecimal;

public class FormatterUtil {

    public static String format(BigDecimal number){
        return number.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

}
