package com.ryane.banner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Creator: lijianchang
 * Create Time: 2017/7/1.
 * Email: lijianchang@yy.com
 */

public class FadeInFadeOutTransformer implements ViewPager.PageTransformer {
    private static final float MAX_ALPHA = 0.8f;
    private static final float MIN_SCALE = 0.5f;


    @Override
    public void transformPage(View view, float position) {

        if (position < -1) {
        } else if (position <= 1) {
            if (position < 0) {
                // A页执行
                view.setAlpha(1 + position * MAX_ALPHA);
                view.setScaleX(1 + position * MIN_SCALE);
                view.setScaleY(1 + position * MIN_SCALE);
            } else {
                // B页执行
                view.setAlpha(1 - position * MAX_ALPHA);
                view.setScaleX(1 - position * MIN_SCALE);
                view.setScaleY(1 - position * MIN_SCALE);
            }
        } else {
        }
    }
}
