package com.ryane.banner_lib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.ryane.banner_lib.scroller.AutoPlayScroller;

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
    private List<AdPageInfo> mInfos;
    private Handler mUIHandler;

    public static int INTERVAL = 3000;  // 默认间隔时间

    private int mSelectedIndex = 0;     // 当前下标

    public ScrollerPager(Context context) {
        this(context, null);
    }

    public ScrollerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mUIHandler = new Handler(Looper.getMainLooper());
    }

    public ScrollerPager(RelativeLayout mContainer, List<AdPageInfo> infos) {
        super(mContainer.getContext());

        this.mContainer = mContainer;
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
        ScrollerPagerAdapter adapter = new ScrollerPagerAdapter(getContext(), mInfos);
        setAdapter(adapter);
        setOnPageChangeListener(mOnPageChangeListener);

        initScrollTime( new AutoPlayScroller(getContext(), new LinearInterpolator()));
    }

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
        stopAdvertPlay();
        mUIHandler.postDelayed(mImageTimmerTask, INTERVAL);
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
        public void onPageSelected(int position) {
            Log.d("ScrollerPager", "position = " + position);
            mSelectedIndex = position;
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
     * 设置切换动画
     *
     * @param transfromer
     */
    public void setPageTransfromer(PageTransformer transfromer) {
        setPageTransformer(true, transfromer);
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
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            mContainer.addView(this, layoutParams);


/*            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,100);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(R.drawable.photo2);
            mContainer.addView(imageView, layoutParams);*/

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
