package com.ryane.banner_lib;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ryane.banner_lib.laoder.ImageLoaderManager;
import com.ryane.banner_lib.transformer.DepthPageTransformer;
import com.ryane.banner_lib.transformer.RotateDownTransformer;
import com.ryane.banner_lib.transformer.TransfromerManager;
import com.ryane.banner_lib.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/17.
 * Email: lijianchang@yy.com
 */

public class AdPlayView extends RelativeLayout {
    private List<AdPageInfo> mInfos;
    private ScrollerPager mScrollerPager;

    public AdPlayView(Context context) {
        this(context, null);
    }

    public AdPlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AdPlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setInfoList(List<AdPageInfo> pageInfos) {
        if (null != pageInfos)
            this.mInfos = pageInfos;
        else
            this.mInfos = new ArrayList<>();
        mScrollerPager = new ScrollerPager(this, mInfos);
    }

    public void setUp() {
        mScrollerPager.show();
    }

    /**
     * 设置自动滑动间隔时间
     *
     * @param interval
     */
    public void setInterval(int interval) {
        ScrollerPager.INTERVAL = interval;
    }

    /**
     * 设置图片加载方式
     * @param type
     */
    public void setImageLoadType(int type) {
        ImageLoaderManager.getInstance().setImageLoaderType(type);
    }

    /**
     * 设置切换动画
     * @param transfromer
     */
    public void setPageTransfromer(int transfromer){
        switch (transfromer) {
            case TransfromerManager.TRANSFORMER_DEPTH_PAGE:
                mScrollerPager.setPageTransfromer(new DepthPageTransformer());
                break;
            case TransfromerManager.TRANSFORMER_ZOOM_OUT_PAGE:
                mScrollerPager.setPageTransfromer(new ZoomOutPageTransformer());
                break;
            case TransfromerManager.TRANSFORMER_ROTATE_DOWN:
                mScrollerPager.setPageTransfromer(new RotateDownTransformer());
                break;
            default:
            case TransfromerManager.TRANSFORMER_DEFAULT:
                mScrollerPager.setPageTransfromer(null);
                break;
        }
    }



}
