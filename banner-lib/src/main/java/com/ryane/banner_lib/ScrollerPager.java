package com.ryane.banner_lib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.ryane.banner_lib.indicator.IndicatorManager;
import com.ryane.banner_lib.scroller.AutoPlayScroller;
import com.ryane.banner_lib.view.NumberView;
import com.ryane.banner_lib.view.PointView;
import com.ryane.banner_lib.view.TitleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/17.
 * Email: lijianchang@yy.com
 */

public class ScrollerPager extends ViewPager {
    private RelativeLayout mContainer;
    private TitleView mTitleView;
    private LinearLayout mIndicator;
    private LinearLayout mPageNumberLayout;
    private List<AdPageInfo> mInfos;
    private Handler mUIHandler;
    private PointView[] mPointViews = null;
    private NumberView[] mNumberViews = null;
    private ScrollerPagerAdapter adapter;

    private int mPointViewSelectedColor = ContextCompat.getColor(getContext(), R.color.point_selected_color);
    private int mPointViewNormalColor = ContextCompat.getColor(getContext(), R.color.point_normal_color);

    public static ViewPager.PageTransformer mTransformer = null;
    public static boolean mAutoPlay = true;    // 是否自动播放
    public static int mInterval = 3000;  // 默认间隔时间

    private int mSelectedIndex = 0;     // 当前下标

    public ScrollerPager(Context context) {
        this(context, null);
    }

    public ScrollerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mUIHandler = new Handler(Looper.getMainLooper());
    }

    public ScrollerPager(RelativeLayout mContainer, TitleView mTitleView, List<AdPageInfo> infos) {
        super(mContainer.getContext());

        this.mContainer = mContainer;
        this.mTitleView = mTitleView;

        if (null != infos) {
            this.mInfos = infos;
        } else {
            this.mInfos = new ArrayList<>();
        }

        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        mUIHandler = new Handler(Looper.getMainLooper());

        // 初始化Indicator
        initIndicator();
        // 初始化数字页码
        initPageNumber();
        // 初始化切换时间
        initScrollTime(new AutoPlayScroller(getContext(), new LinearInterpolator()));
        // 初始化切换动画
        initTransformer(true);

        // 初始化viewpager start
        adapter = new ScrollerPagerAdapter(getContext(), mInfos);
        setAdapter(adapter);
        setOnPageChangeListener(mOnPageChangeListener);
        // 初始化viewpager end
    }

    /**
     * 初始化Indicator
     */
    private void initIndicator() {
        if (IndicatorManager.getIndicatorType() == IndicatorManager.POINT_INDICATOR) {
            mIndicator = new LinearLayout(getContext());
            mIndicator.setOrientation(LinearLayout.HORIZONTAL);
            mIndicator.setGravity(Gravity.CENTER);

            mIndicator.removeAllViews();

            mPointViews = new PointView[mInfos.size()];

            float pointSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

            for (int i = 0; i < mInfos.size(); i++) {
                PointView pointView;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) pointSize, (int) pointSize);
                layoutParams.leftMargin = (int) (pointSize / 2);
                layoutParams.rightMargin = (int) (pointSize / 2);
                if (i == mSelectedIndex) {
                    pointView = new PointView(getContext(), pointSize, ContextCompat.getColor(getContext(), R.color.point_selected_color));
                } else {
                    pointView = new PointView(getContext(), pointSize, ContextCompat.getColor(getContext(), R.color.point_normal_color));
                }
                if (i == 0) {
                    layoutParams.leftMargin = 0;
                }
                if (i == mInfos.size() - 1) {
                    layoutParams.rightMargin = 0;
                }
                layoutParams.topMargin = (int) pointSize;
                layoutParams.bottomMargin = (int) pointSize;
                mPointViews[i] = pointView;
                mIndicator.addView(mPointViews[i], layoutParams);
            }
        }
    }

    /**
     * 初始化数字页码
     */
    private void initPageNumber() {
        if (IndicatorManager.getIndicatorType() == IndicatorManager.NUMBER_INDICATOR) {
            mPageNumberLayout = new LinearLayout(getContext());
            mPageNumberLayout.setOrientation(LinearLayout.HORIZONTAL);
            mPageNumberLayout.setGravity(Gravity.CENTER);

            mPageNumberLayout.removeAllViews();

            mNumberViews = new NumberView[mInfos.size()];

            for (int i = 0; i < mInfos.size(); i++) {
                NumberView numberView;
                if (i == mSelectedIndex) {
                    numberView = new NumberView(getContext(), NumberView.mNumberViewSelectedColor);
                } else {
                    numberView = new NumberView(getContext(), NumberView.mNumberViewNormalColor);
                }
                numberView.setNumber(i + 1);
                mNumberViews[i] = numberView;
                mPageNumberLayout.addView(mNumberViews[i]);
            }
        }
    }

    /**
     * 图片A开始滑动到图片B之间的时间
     * 主要是保证两张图片之间的切换动画有足够的时间显示
     *
     * @param mScroller
     */
    private void initScrollTime(Scroller mScroller) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mField.set(this, mScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开始广告滚动任务
     */
    private void startAdvertPlay() {
        if (mAutoPlay == true) {
            stopAdvertPlay();
            mUIHandler.postDelayed(mImageTimmerTask, mInterval);
        }
    }

    /**
     * 停止广告滚动任务
     */
    private void stopAdvertPlay() {
        mUIHandler.removeCallbacks(mImageTimmerTask);
    }

    /**
     * 轮播图片状态监听器
     */
    private OnPageChangeListener mOnPageChangeListener = new SimpleOnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            Log.d("ScrollerPager", "position = " + position);
            mSelectedIndex = position;
            int rightPos = position % mInfos.size();
            if (mTitleView != null) {
                mTitleView.setTitle(mInfos.get(rightPos).getTitle());
            }

            if (IndicatorManager.getIndicatorType() == IndicatorManager.POINT_INDICATOR && mPointViews != null && mPointViews.length > 0) {
                mPointViews[rightPos].setmColor(mPointViewSelectedColor);
                for (int i = 0; i < mPointViews.length; i++) {
                    if (rightPos != i) {
                        mPointViews[i].setmColor(mPointViewNormalColor);
                    }
                }
            }

            if (IndicatorManager.getIndicatorType() == IndicatorManager.NUMBER_INDICATOR && mPageNumberLayout != null && mNumberViews.length > 0) {
                mNumberViews[rightPos].setNumberViewColor(NumberView.mNumberViewSelectedColor);
                for (int i = 0; i < mNumberViews.length; i++) {
                    if (rightPos != i) {
                        mNumberViews[i].setNumberViewColor(NumberView.mNumberViewNormalColor);
                    }
                }
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startAdvertPlay();
            }
        }
    };

    /**
     * 自动播放任务
     */
    private Runnable mImageTimmerTask = new Runnable() {
        @Override
        public void run() {
            if (mSelectedIndex == Integer.MAX_VALUE) {
                int rightPos = mSelectedIndex % mInfos.size();
                setCurrentItem(getInitPosition() + rightPos + 1, true);
            } else {
                setCurrentItem(mSelectedIndex + 1, true);
            }
        }
    };

    /**
     * 获取banner的初始位置
     *
     * @return
     */
    private int getInitPosition() {
        if (mInfos.isEmpty()) {
            return 0;
        }
        int halfValue = Integer.MAX_VALUE / 2;
        int position = halfValue % mInfos.size();
        return halfValue - position;
    }

    /**
     * 设置数字页码的颜色
     *
     * @param normalColor   数字正常背景颜色
     * @param selectedColor 数字选中背景颜色
     * @param numberColor   数字字体颜色
     */
    public void setNumberViewColor(int normalColor, int selectedColor, int numberColor) {
        NumberView.mNumberViewNormalColor = normalColor;
        NumberView.mNumberViewSelectedColor = selectedColor;
        NumberView.mNumberTextColor = numberColor;
        initPageNumber();
    }


    public void setmTitleView(TitleView mTitleView) {
        this.mTitleView = mTitleView;
    }

    /**
     * 设置切换动画
     *
     * @param reverseDrawingOrder 是否有切换动画
     */
    private void initTransformer(boolean reverseDrawingOrder) {
        setPageTransformer(true, mTransformer);
    }

    /**
     * 显示播放ScrollerPager
     */
    public void show() {
        mContainer.removeAllViews();
        if (null == mInfos || 0 == mInfos.size()) {
            stopAdvertPlay();
            mContainer.setVisibility(GONE);
        } else {
            addScrollerPager(); // 把ScrollerPager装载到外布局中
            addIndicatorView(); // 把IndicatorView装载到外布局中
            addPageNumberView();// 把PageNumberView装载到外布局
            addTitleView();     // 把TitleView装载到外布局中

            if (mInfos.size() == 1) {
                stopAdvertPlay();
            } else {
                startAdvertPlay();
            }
            mContainer.setVisibility(VISIBLE);
            // 设置初始化的位置
            setCurrentItem(getInitPosition());
        }
    }

    /**
     * 装载ScrollerPager
     */
    private void addScrollerPager() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mContainer.addView(this, layoutParams);
    }

    /**
     * 装载PageNumberView
     */
    private void addPageNumberView() {
        if (IndicatorManager.getIndicatorType() == IndicatorManager.NUMBER_INDICATOR && mPageNumberLayout != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getContext().getResources().getDisplayMetrics());
            mContainer.addView(mPageNumberLayout, layoutParams);
        }
    }

    /**
     * 装载IndicatorView
     */
    private void addIndicatorView() {
        if (IndicatorManager.getIndicatorType() == IndicatorManager.POINT_INDICATOR && mIndicator != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getContext().getResources().getDisplayMetrics());
            mContainer.addView(mIndicator, layoutParams);
        }
    }

    /**
     * 装载TitleView
     */
    private void addTitleView() {
        if (mTitleView != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            switch (mTitleView.gravity) {
                default:
                case TitleView.ALIGN_PARENT_BOTTOM:
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    break;
                case TitleView.ALIGN_PARENT_TOP:
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    break;
                case TitleView.CENTER_IN_PARENT:
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
            }
            layoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTitleView.marginTop, getContext().getResources().getDisplayMetrics());
            layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTitleView.marginBottom, getContext().getResources().getDisplayMetrics());
            layoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTitleView.marginLeft, getContext().getResources().getDisplayMetrics());
            layoutParams.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTitleView.marginRight, getContext().getResources().getDisplayMetrics());
            mContainer.addView(mTitleView, layoutParams);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopAdvertPlay();
                break;
            case MotionEvent.ACTION_UP:
                startAdvertPlay();
                break;
        }
        return super.onTouchEvent(ev);
    }

}
