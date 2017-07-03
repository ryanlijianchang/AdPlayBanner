package com.ryane.banner_lib.indicator;

/**
 * Creator: lijianchang
 * Create Time: 2017/7/1.
 * Email: lijianchang@yy.com
 */

public class IndicatorManager {
    public static final int NONE_INDICATOR = 0;     // 没有indicator
    public static final int POINT_INDICATOR = 1;    // 点型indicator
    public static final int NUMBER_INDICATOR = 2;   // 数字型indicator

    private static int mIndicatorType = NONE_INDICATOR;

    public static void setIndicatorType(int indicatorType) {
        mIndicatorType = indicatorType;
    }

    public static int getIndicatorType() {
        return mIndicatorType;
    }
}
