package com.ryane.banner_lib.scroller;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/19.
 * Email: lijianchang@yy.com
 */

public class AutoPlayScroller extends Scroller {

    // 自动切换动画时长
    private int animTime = 400;

    public AutoPlayScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, animTime);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, animTime);
    }

}

