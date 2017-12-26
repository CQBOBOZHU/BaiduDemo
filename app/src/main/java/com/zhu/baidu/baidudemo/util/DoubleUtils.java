package com.zhu.baidu.baidudemo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by BoBoZhu on 2017/10/9.
 */

public class DoubleUtils {


    public static String getT(double value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }

    /**
     * 和
     *
     * @param value1 *
     * @param value2 *
     * @return
     */
    public static Double add(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }


    /**
     * * 两个Double数相除，并保留scale位小数 *
     *
     * @param v1    *
     * @param v2    *
     * @param scale *
     * @return Double
     */
    public static Double div(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public static Double multiply(Double v1, Double v2){
        BigDecimal a1 = new BigDecimal(Double.toString(v1));
        BigDecimal b1 = new BigDecimal(Double.toString(v2));
       return a1.multiply(b1).doubleValue();
    }
}
