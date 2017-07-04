package com.ryane.banner_lib;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ryane.banner_lib.indicator.IndicatorManager;
import com.ryane.banner_lib.laoder.ImageLoaderManager;
import com.ryane.banner_lib.sort.QuickSort;
import com.ryane.banner_lib.view.NumberView;
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

    public AdPlayBanner setBannerBackground(int color) {
        setBackgroundColor(color);
        return this;
    }

    /**
     * 设置指示器类型
     * @param type
     * @return
     */
    public AdPlayBanner setIndicatorType(IndicatorType type) {
        IndicatorManager.getInstance().setIndicatorType(type);
        return this;
    }

    /**
     * 设置数据源
     *
     * @param pageInfos
     */
    public AdPlayBanner setInfoList(ArrayList<AdPageInfo> pageInfos) {
        if (null != pageInfos)
            this.mInfos = QuickSort.quickSort(pageInfos, 0, pageInfos.size() - 1);
        else
            this.mInfos = new ArrayList<>();
        return this;
    }

    /**
     * 设置自动滑动间隔时间
     *
     * @param interval
     */
    public AdPlayBanner setInterval(int interval) {
        ScrollerPager.mInterval = interval;
        return this;
    }

    /**
     * 设置图片加载方式
     *
     * @param type
     */
    public AdPlayBanner setImageLoadType(ImageLoaderType type) {
        ImageLoaderManager.getInstance().setImageLoaderType(type);
        return this;
    }

    /**
     * 设置切换动画
     * 如果不设置动画，设置为null
     * @param transformer
     */
    public AdPlayBanner setPageTransfromer(ViewPager.PageTransformer transformer) {
        if (mScrollerPager != null) {
            mScrollerPager.setPageTransformer(true, transformer);
        } else {
            mScrollerPager.mTransformer = transformer;
        }
        return this;
    }


    /**
     * 设置数字页码的颜色
     * @param normalColor   数字正常背景颜色
     * @param selectedColor 数字选中背景颜色
     * @param numberColor   数字字体颜色
     */
    public AdPlayBanner setNumberViewColor(int normalColor, int selectedColor, int numberColor) {
        if (mScrollerPager != null) {
            mScrollerPager.setNumberViewColor(normalColor, selectedColor, numberColor);
        } else {
            NumberView.mNumberViewNormalColor = normalColor;
            NumberView.mNumberViewSelectedColor = selectedColor;
            NumberView.mNumberTextColor = numberColor;
        }
        return this;
    }

    /**
     * 设置事件点击监听器
     * @param l
     * @return
     */
    public AdPlayBanner setOnPageClickListener(OnPageClickListener l) {
        ImageLoaderManager.mOnPageClickListener = l;
        return this;
    }

    /**
     * 设置图片显示方式
     * @param scaleType
     * @return
     */
    public AdPlayBanner setImageViewScaleType(ScaleType scaleType){
        ImageLoaderManager.getInstance().setmScaleType(scaleType);
        return this;
    }

    /**
     * 设置是否自动播放
     * 默认为true 自动播放
     * @param autoPlay
     * @return
     */
    public AdPlayBanner setAutoPlay(boolean autoPlay) {
        ScrollerPager.mAutoPlay = autoPlay;
        return this;
    }

    /**
     * 装载AdPlayBanner
     */
    public void setUp() {
        mScrollerPager = new ScrollerPager(this, mTitleView, mInfos);
        mScrollerPager.show();
    }

    public interface OnPageClickListener {
        void onPageClick(AdPageInfo info, int postion);
    }


    public enum ScaleType {
        FIT_XY      (1),
        FIT_START   (2),
        FIT_CENTER  (3),
        FIT_END     (4),
        CENTER      (5),
        CENTER_CROP (6),
        CENTER_INSIDE (7);
        ScaleType(int ni) {
            nativeInt = ni;
        }
        final int nativeInt;
    }

    public enum ImageLoaderType {
        FRESCO (1),
        GLIDE (2),
        PICASSO (3);
        ImageLoaderType(int ni) {
            nativeInt = ni;
        }
        final int nativeInt;
    }

    public enum IndicatorType {
        NONE_INDICATOR (0),
        NUMBER_INDICATOR (1),
        POINT_INDICATOR (2);
        IndicatorType(int ni) {
            nativeInt = ni;
        }
        final int nativeInt;
    }

}
