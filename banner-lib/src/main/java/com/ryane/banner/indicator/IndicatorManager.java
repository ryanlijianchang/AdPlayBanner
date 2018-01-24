package com.ryane.banner.indicator;

import com.ryane.banner.AdPlayBanner;

import static com.ryane.banner.AdPlayBanner.IndicatorType.NONE_INDICATOR;

/**
 * Create Time: 2017/7/1.
 * @author RyanLee
 */

public class IndicatorManager {


    private static class SingletonHolder {
        private static final IndicatorManager INSTANCE = new IndicatorManager();
    }

    private IndicatorManager() {
    }

    public static IndicatorManager getInstance() {
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
