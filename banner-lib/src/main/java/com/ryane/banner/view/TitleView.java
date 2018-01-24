package com.ryane.banner.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryane.banner.R;

import static com.ryane.banner.view.TitleView.Gravity.PARENT_BOTTOM;

/**
 * Create Time: 2017/6/30.
 * @author RyanLee
 */

public class TitleView extends RelativeLayout {
    /**
     * 标题
     */
    private TextView mTitle;
    /**
     * 外布局
     */
    private RelativeLayout mContainer;

    /**
     * 位置
     */
    public enum Gravity {
        /**
         * 父布局顶部
         */
        PARENT_TOP(0),
        /**
         * 父布局底部
         */
        PARENT_BOTTOM(1),
        /**
         * 父布局中间
         */
        PARENT_CENTER(2);

        Gravity(int ni) {
            nativeInt = ni;
        }

        final int nativeInt;
    }

    /**
     * 默认在父布局中间位置
     */
    public Gravity gravity = PARENT_BOTTOM;

    /**
     * 标题的TopMargin
     */
    public int marginTop = 0;
    /**
     * 标题的BottomMargin
     */
    public int marginBottom = 0;
    /**
     * 标题的LeftMargin
     */
    public int marginLeft = 0;
    /**
     * 标题的RightMargin
     */
    public int marginRight = 0;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }


    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title, this, true);
        mTitle = findViewById(R.id.title);
        mContainer = findViewById(R.id.titleContainer);
    }

    /**
     * 获取一个默认的TitleView
     *
     * @param context 上下文
     * @return TitleView
     */
    public static TitleView getDefaultTitleView(Context context) {
        TitleView titleView = new TitleView(context);
        titleView.setPosition(PARENT_BOTTOM)
                .setTitleMargin(0, 0, 0, 20)
                .setTitlePadding(2, 5, 2, 5)
                .setViewBackground(ContextCompat.getColor(context, R.color.grey))
                .setTitleColor(ContextCompat.getColor(context, R.color.white))
                .setTitleSize(15);
        return titleView;
    }


    /**
     * 设置标题内容
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        if (mTitle != null && !TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    /**
     * 设置字体大小
     *
     * @param size 大小
     * @return TitleView
     */
    public TitleView setTitleSize(int size) {
        if (mTitle != null) {
            mTitle.setTextSize(size);
        }
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param color 颜色
     * @return TitleView
     */
    public TitleView setTitleColor(int color) {
        if (mTitle != null) {
            mTitle.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置标题背景
     *
     * @param color 背景
     * @return TitleView
     */
    public TitleView setViewBackground(int color) {
        mContainer.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题在Banner的位置
     *
     * @param gravity 位置
     * @return TitleView
     */
    public TitleView setPosition(Gravity gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * 设置标题的上下左右的Margin
     *
     * @param left   左
     * @param top    上
     * @param right  右
     * @param bottom 下
     * @return TitleView
     */
    public TitleView setTitleMargin(int left, int top, int right, int bottom) {
        this.marginTop = top;
        this.marginBottom = bottom;
        this.marginLeft = left;
        this.marginRight = right;
        return this;
    }

    /**
     * 设置标题的padding值
     *
     * @param left   左
     * @param top    上
     * @param right  右
     * @param bottom 下
     * @return TitleView
     */
    public TitleView setTitlePadding(int left, int top, int right, int bottom) {
        if (mTitle != null) {
            mTitle.setPadding(change2Dp(left), change2Dp(top), change2Dp(right), change2Dp(bottom));
        }
        return this;
    }

    private int change2Dp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics());
    }
}
