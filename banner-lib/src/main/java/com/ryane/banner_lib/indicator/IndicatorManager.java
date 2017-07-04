package com.ryane.banner_lib.indicator;

import com.ryane.banner_lib.AdPlayBanner;

import static com.ryane.banner_lib.AdPlayBanner.IndicatorType.NONE_INDICATOR;

/**
 * Creator: lijianchang
 * Create Time: 2017/7/1.
 * Email: lijianchang@yy.com
 */

public class IndicatorManager {


    private static class SingletonHolder {
        private static final IndicatorManager INSTANCE = new IndicatorManager();
    }

    private IndicatorManager() {
    }

    public static final IndicatorManager getInstance() {
        return IndicatorManager.SingletonHolder.INSTANCE;
    }

    private AdPlayBanner.IndicatorType mIndicatorType = NONE_INDICATOR;

    public void setIndicatorType(AdPlayBanner.IndicatorType indicatorType) {
        mIndicatorType = indicatorType;
    }

    public AdPlayBanner.IndicatorType getIndicatorType() {
        return mIndicatorType;
    }
}
