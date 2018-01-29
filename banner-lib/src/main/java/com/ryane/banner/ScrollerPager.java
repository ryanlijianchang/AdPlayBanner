package com.ryane.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.ryane.banner.indicator.IndicatorManager;
import com.ryane.banner.scroller.AutoPlayScroller;
import com.ryane.banner.util.ListUtils;
import com.ryane.banner.util.OsUtil;
import com.ryane.banner.view.NumberView;
import com.ryane.banner.view.PointView;
import com.ryane.banner.view.TitleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.ryane.banner.AdPlayBanner.IndicatorType.NUMBER_INDICATOR;
import static com.ryane.banner.AdPlayBanner.IndicatorType.POINT_INDICATOR;

/**
 * Create Time: 2017/6/17.
 * @author RyanLee
 */

public class ScrollerPager extends ViewPager {
    /**
     * 父布局
     */
    private RelativeLayout mContainer;
    /**
     * 标题控件
     */
    private TitleView mTitleView;
    /**
     * 指示器控件
     */
    private LinearLayout mIndicator;
    /**
     * 数字指示器控件
     */
    private LinearLayout mPageNumberLayout;
    /**
     * 数据源
     */
    private List<AdPageInfo> mDataList;
    /**
     * UI线程的Handler
     */
    private Handler mUIHandler = new Handler(Looper.myLooper());
    /**
     * 点型指示器
     */
    private PointView[] mPointViews = null;
    /**
     * 数字型指示器
     */
    private NumberView[] mNumberViews = null;
    /**
     * 点型指示器被选中的颜色
     */
    private final int POINT_VIEW_SELECTED_COLOR = ContextCompat.getColor(getContext(), R.color.point_selected_color);
    /**
     * 点型指示器默认的颜色
     */
    private final int POINT_VIEW_NORMAL_COLOR = ContextCompat.getColor(getContext(), R.color.point_normal_color);
    /**
     * 切换动画
     */
    public static ViewPager.PageTransformer mTransformer = null;
    /**
     * 是否自动播放
     */
    public static boolean mAutoPlay = true;
    /**
     * 是否可以手动滑动
     */
    public static boolean canScroll = true;
    /**
     * 间隔时间，默认2000ms
     */
    public static int mInterval = 2000;
    /**
     * 当前下标
     */
    private int mSelectedIndex = 0;
    /**
     * 滑动监听
     */
    private AdPlayBanner.OnPagerChangeListener mPageListener;

    public ScrollerPager(Context context) {
        this(context, null);
    }

    public ScrollerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollerPager(RelativeLayout mContainer, TitleView mTitleView, List<AdPageInfo> infos) {
        super(mContainer.getContext());

        this.mContainer = mContainer;
        this.mTitleView = mTitleView;

        if (null != infos) {
            this.mDataList = infos;
        } else {
            this.mDataList = new ArrayList<>();
        }

        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        // 初始化Indicator
        initIndicator();
        // 初始化数字页码
        initPageNumber();
        // 初始化切换时间
        initScrollTime(new AutoPlayScroller(getContext(), new LinearInterpolator()));
        // 初始化切换动画
        initTransformer();

        // 初始化viewpager start
        ScrollerPagerAdapter adapter = new ScrollerPagerAdapter(getContext(), mDataList);
        setAdapter(adapter);
        addOnPageChangeListener(mOnPageChangeListener);
        // 初始化viewpager end
    }

    /**
     * 初始化Indicator
     */
    private void initIndicator() {
        // 如果是点型指示器
        if (IndicatorManager.getInstance().getIndicatorType() == POINT_INDICATOR) {
            mIndicator = new LinearLayout(getContext());
            mIndicator.setOrientation(LinearLayout.HORIZONTAL);
            mIndicator.setGravity(Gravity.CENTER);

            mIndicator.removeAllViews();

            mPointViews = new PointView[mDataList.size()];

            // 点的大小为5dp
            float pointSize = OsUtil.dpToPx(5);

            int size = mDataList.size();
            for (int i = 0; i <size; i++) {
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
                if (i == size - 1) {
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
        // 如果是数字指示器
        if (IndicatorManager.getInstance().getIndicatorType() == NUMBER_INDICATOR) {
            mPageNumberLayout = new LinearLayout(getContext());
            mPageNumberLayout.setOrientation(LinearLayout.HORIZONTAL);
            mPageNumberLayout.setGravity(Gravity.CENTER);

            mPageNumberLayout.removeAllViews();

            mNumberViews = new NumberView[mDataList.size()];

            int size = mDataList.size();
            for (int i = 0; i < size; i++) {
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
     * @param mScroller 滑动Scroller
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
        if (mAutoPlay) {
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
            if (mPageListener != null) {
                int pos = (mDataList == null || mDataList.size() == 0) ? 0 : position % mDataList.size();
                mPageListener.onPageSelected(pos);
            }
            if (ListUtils.isEmpty(mDataList)) {
                return;
            }
            mSelectedIndex = position;
            int rightPos = position % mDataList.size();
            if (mTitleView != null) {
                mTitleView.setTitle(mDataList.get(rightPos).getTitle());
            }

            if (IndicatorManager.getInstance().getIndicatorType() == POINT_INDICATOR && mPointViews != null && mPointViews.length > 0) {
                mPointViews[rightPos].setPointViewColor(POINT_VIEW_SELECTED_COLOR);
                for (int i = 0; i < mPointViews.length; i++) {
                    if (rightPos != i) {
                        mPointViews[i].setPointViewColor(POINT_VIEW_NORMAL_COLOR);
                    }
                }
            }

            if (IndicatorManager.getInstance().getIndicatorType() == NUMBER_INDICATOR && mPageNumberLayout != null && mNumberViews.length > 0) {
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
            if (mPageListener != null) {
                int pos = (mDataList == null || mDataList.size() == 0) ? 0 : position % mDataList.size();
                mPageListener.onPageScrolled(pos, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mPageListener != null) {
                mPageListener.onPageScrollStateChanged(state);
            }
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
                if (ListUtils.isEmpty(mDataList)) {
                    return;
                }
                int rightPos = mSelectedIndex % mDataList.size();
                setCurrentItem(getInitPosition() + rightPos + 1, true);
            } else {
                setCurrentItem(mSelectedIndex + 1, true);
            }
        }
    };

    /**
     * 获取banner的初始位置
     *
     * @return banner的初始位置
     */
    private int getInitPosition() {
        if (ListUtils.isEmpty(mDataList)) {
            return 0;
        }
        int halfValue = Integer.MAX_VALUE / 2;
        int position = halfValue % mDataList.size();
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


    public void setTitleView(TitleView mTitleView) {
        this.mTitleView = mTitleView;
    }

    /**
     * 设置切换动画
     */
    private void initTransformer() {
        if (mTransformer == null) {
            return;
        }
        setPageTransformer(true, mTransformer);
    }

    /**
     * 显示播放ScrollerPager
     */
    public void show() {
        mContainer.removeAllViews();
        if (null == mDataList || 0 == mDataList.size()) {
            stopAdvertPlay();
            mContainer.setVisibility(GONE);
        } else {
            addScrollerPager(); // 把ScrollerPager装载到外布局中
            addIndicatorView(); // 把IndicatorView装载到外布局中
            addPageNumberView();// 把PageNumberView装载到外布局
            addTitleView();     // 把TitleView装载到外布局中

            if (mDataList.size() == 1) {
                stopAdvertPlay();
            } else {
                startAdvertPlay();
            }
            mContainer.setVisibility(VISIBLE);
            // 设置初始化的位置
            setCurrentItem(getInitPosition());
        }
    }

    public void stop() {
        stopAdvertPlay();
    }

    /**
     * 装载ScrollerPager
     */
    private void addScrollerPager() {
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
        mContainer.addView(this, layoutParams);
    }

    /**
     * 装载PageNumberView
     */
    private void addPageNumberView() {
        if (IndicatorManager.getInstance().getIndicatorType() == NUMBER_INDICATOR && mPageNumberLayout != null) {
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
        if (IndicatorManager.getInstance().getIndicatorType() == POINT_INDICATOR && mIndicator != null) {
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
                case PARENT_BOTTOM:
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    break;
                case PARENT_TOP:
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    break;
                case PARENT_CENTER:
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
            }
            layoutParams.topMargin = OsUtil.dpToPx(mTitleView.marginTop);
            layoutParams.bottomMargin = OsUtil.dpToPx(mTitleView.marginBottom);
            layoutParams.leftMargin = OsUtil.dpToPx(mTitleView.marginLeft);
            layoutParams.rightMargin = OsUtil.dpToPx(mTitleView.marginRight);
            mContainer.addView(mTitleView, layoutParams);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 如果点击
                performClick();
                stopAdvertPlay();
                break;
            case MotionEvent.ACTION_UP:
                startAdvertPlay();
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setmPageListener(AdPlayBanner.OnPagerChangeListener mPageListener) {
        this.mPageListener = mPageListener;
    }
}
