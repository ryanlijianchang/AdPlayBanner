package com.ryane.banner_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ryane.banner_lib.laoder.ImageLoaderManager;
import com.ryane.banner_lib.transformer.DepthPageTransformer;
import com.ryane.banner_lib.transformer.RotateDownTransformer;
import com.ryane.banner_lib.transformer.TransfromerManager;
import com.ryane.banner_lib.transformer.ZoomOutPageTransformer;
import com.ryane.banner_lib.view.TitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/17.
 * Email: lijianchang@yy.com
 */

public class AdPlayBanner extends RelativeLayout {
    private List<AdPageInfo> mInfos;
    private ScrollerPager mScrollerPager;
    private TitleView mTitleView;
    private boolean mHasIndicator = false;

    public AdPlayBanner(Context context) {
        this(context, null);
    }

    public AdPlayBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdPlayBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 初始化标题视图
     */
    public AdPlayBanner addTitleView(TitleView mTitleView) {
        this.mTitleView = mTitleView;
        if (mScrollerPager != null) {
            mScrollerPager.setmTitleView(mTitleView);
        }
        return this;
    }

    public AdPlayBanner hasIndicator(boolean mHasIndicator) {
        this.mHasIndicator = mHasIndicator;
        if (mScrollerPager != null) {
            mScrollerPager.setHasIndicator(mHasIndicator);
        }
        return this;
    }

    /**
     * 设置数据源
     *
     * @param pageInfos
     */
    public AdPlayBanner setInfoList(List<AdPageInfo> pageInfos) {
        if (null != pageInfos)
            this.mInfos = pageInfos;
        else
            this.mInfos = new ArrayList<>();
        mScrollerPager = new ScrollerPager(this, mTitleView, mInfos, mHasIndicator);
        return this;
    }

    /**
     * 设置自动滑动间隔时间
     *
     * @param interval
     */
    public AdPlayBanner setInterval(int interval) {
        ScrollerPager.INTERVAL = interval;
        return this;
    }

    /**
     * 设置图片加载方式
     *
     * @param type
     */
    public AdPlayBanner setImageLoadType(int type) {
        ImageLoaderManager.getInstance().setImageLoaderType(type);
        return this;
    }

    /**
     * 设置切换动画
     *
     * @param transfromer
     */
    public AdPlayBanner setPageTransfromer(int transfromer) {
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
        return this;
    }

    /**
     * 装载AdPlayBanner
     */
    public void setUp() {
        mScrollerPager.show();
    }

}
