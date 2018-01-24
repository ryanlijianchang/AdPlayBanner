package com.ryane.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ryane.banner.indicator.IndicatorManager;
import com.ryane.banner.loader.ImageLoaderManager;
import com.ryane.banner.sort.QuickSort;
import com.ryane.banner.view.NumberView;
import com.ryane.banner.view.TitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Time: 2017/6/17.
 * @author RyanLee
 */

public class AdPlayBanner extends RelativeLayout {
    /**
     * 数据源
     */
    private List<AdPageInfo> mDataList;
    /**
     * 滑动的ViewPager
     */
    private ScrollerPager mScrollerPager;
    /**
     * 标题控件
     */
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
     * @param mTitleView    标题的View
     * @return this
     */
    public AdPlayBanner addTitleView(TitleView mTitleView) {
        this.mTitleView = mTitleView;
        if (mScrollerPager != null) {
            mScrollerPager.setTitleView(mTitleView);
        }
        return this;
    }

    public AdPlayBanner setBannerBackground(int color) {
        setBackgroundColor(color);
        return this;
    }

    /**
     * 设置指示器类型
     *
     * @param type  指示器类型
     * @return this
     */
    public AdPlayBanner setIndicatorType(IndicatorType type) {
        IndicatorManager.getInstance().setIndicatorType(type);
        return this;
    }

    /**
     * 设置数据源
     * @param dataList 数据源
     * @return this
     */
    public AdPlayBanner setInfoList(List<AdPageInfo> dataList) {
        if (null != dataList) {
            this.mDataList = QuickSort.quickSort(dataList, 0, dataList.size() - 1);
        } else {
            this.mDataList = new ArrayList<>();
        }
        return this;
    }

    /**
     * 设置自动滑动间隔时间
     * @param interval 间隔时间
     * @return this
     */
    public AdPlayBanner setInterval(int interval) {
        ScrollerPager.mInterval = interval;
        return this;
    }

    /**
     * 设置图片加载方式
     * @param type 加载方式
     * @return this
     */
    public AdPlayBanner setImageLoadType(ImageLoaderType type) {
        ImageLoaderManager.getInstance().setImageLoaderType(type);
        return this;
    }

    /**
     * 设置切换动画
     * 如果不设置动画，设置为null
     * @param transformer 切换动画
     * @return this
     */
    public AdPlayBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        if (mScrollerPager != null) {
            mScrollerPager.setPageTransformer(true, transformer);
        } else {
            ScrollerPager.mTransformer = transformer;
        }
        return this;
    }

    /**
     * 设置数字页码的颜色
     *
     * @param normalColor   数字正常背景颜色
     * @param selectedColor 数字选中背景颜色
     * @param numberColor   数字字体颜色
     * @return  this
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
     * @param listener 点击监听器
     * @return this
     */
    public AdPlayBanner setOnPageClickListener(OnPageClickListener listener) {
        ImageLoaderManager.getInstance().setOnPageClickListener(listener);
        return this;
    }

    /**
     * 设置图片显示方式
     * @param scaleType 图片显示方式
     * @return this
     */
    public AdPlayBanner setImageViewScaleType(ScaleType scaleType) {
        ImageLoaderManager.getInstance().setScaleType(scaleType);
        return this;
    }

    /**
     * 设置是否自动播放
     * 默认为true 自动播放
     * @param autoPlay 自动播放
     * @return this
     */
    public AdPlayBanner setAutoPlay(boolean autoPlay) {
        ScrollerPager.mAutoPlay = autoPlay;
        return this;
    }

    /**
     * 装载AdPlayBanner
     */
    public void setUp() {
        mScrollerPager = new ScrollerPager(this, mTitleView, mDataList);
        mScrollerPager.show();
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mScrollerPager == null) {
            return;
        }
        mScrollerPager.stop();
        // 清除所有的子view
        removeAllViews();
    }

    public interface OnPageClickListener {
        /**
         * 页面点击
         * @param info    数据
         * @param position 位置
         */
        void onPageClick(AdPageInfo info, int position);
    }


    /**
     * 图片的ScaleType
     */
    public enum ScaleType {
        FIT_XY(1),
        FIT_START(2),
        FIT_CENTER(3),
        FIT_END(4),
        CENTER(5),
        CENTER_CROP(6),
        CENTER_INSIDE(7);

        ScaleType(int ni) {
            nativeInt = ni;
        }

        final int nativeInt;
    }

    /**
     * 图片加载方式
     */
    public enum ImageLoaderType {
        FRESCO(1),
        GLIDE(2),
        PICASSO(3);

        ImageLoaderType(int ni) {
            nativeInt = ni;
        }

        final int nativeInt;
    }

    /**
     * 指示器类型
     */
    public enum IndicatorType {
        /**
         * 空型页码指示器
         */
        NONE_INDICATOR(0),
        /**
         * 数字页码指示器
         */
        NUMBER_INDICATOR(1),
        /**
         * 点型页码指示器
         */
        POINT_INDICATOR(2);

        IndicatorType(int ni) {
            nativeInt = ni;
        }

        final int nativeInt;
    }

}
