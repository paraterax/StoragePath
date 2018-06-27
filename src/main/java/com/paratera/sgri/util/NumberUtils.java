package com.paratera.sgri.util;

import java.math.BigDecimal;

public final class NumberUtils {
    public static final Double toFixed(int precision, Double value) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
