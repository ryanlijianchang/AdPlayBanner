package com.ryane.banner_lib.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/19.
 * Email: lijianchang@yy.com
 */

public class RotateDownTransformer implements ViewPager.PageTransformer {

    // 旋转的最大角度为20度
    private static final float MAX_ROTATE = 20.0f;
    // 旋转过程中的角度
    private float currentRotate;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) {
            view.setRotation(0);
        } else if (position <= 0) {
            // position范围[-1.0,0.0],此时A页动画移出屏幕
            currentRotate = position * MAX_ROTATE;
            // 设置当前页的旋转中心点，横坐标是屏幕宽度的1/2,纵坐标为屏幕的高度
            view.setPivotX(pageWidth / 2);
            view.setPivotY(view.getHeight());
            view.setRotation(currentRotate);
        } else if (position <= 1) {
            // position范围(0.0,1.0],此时B页动画移到屏幕
            currentRotate = position * MAX_ROTATE;
            // 设置当前页的旋转中心点，横坐标是屏幕宽度的1/2,纵坐标为屏幕的高度
            view.setPivotX(pageWidth / 2);
            view.setPivotY(view.getHeight());
            view.setRotation(currentRotate);
        } else {
            view.setRotation(0);
        }
    }
}
