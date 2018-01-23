package com.ryane.banner.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryane.banner.R;

import static com.ryane.banner.view.TitleView.Gravity.PARENT_BOTTOM;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/30.
 * Email: lijianchang@yy.com
 */

public class TitleView extends RelativeLayout {
    private TextView mTitle;
    private RelativeLayout mContainer;

    public enum Gravity {
        PARENT_TOP (0),
        PARENT_BOTTOM (1),
        PARENT_CENTER (2);
        Gravity(int ni) {
            nativeInt = ni;
        }
        final int nativeInt;
    }

    public Gravity gravity = PARENT_BOTTOM;    // 在父布局中的位置
    public int marginTop, marginBottom, marginLeft, marginRight = 0;

    public TitleView(Context context) {
        this(context, null);
    }

    /**
     *  获取一个默认的TitleView
     * @param context
     * @return
     */
    public static TitleView getDefaultTitleView(Context context){
        TitleView titleView = new TitleView(context);
        titleView.setPosition(PARENT_BOTTOM)
                .setTitleMargin(0, 0, 0, 20)
                .setTitlePadding(2,5,2,5)
                .setViewBackground(ContextCompat.getColor(context, R.color.grey))
                .setTitleColor(ContextCompat.getColor(context, R.color.white))
                .setTitleSize(15);
        return titleView;
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title, this);
        mTitle = (TextView) findViewById(R.id.title);
        mContainer = (RelativeLayout) findViewById(R.id.titleContainer);
    }

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title + "");
        }
    }

    /**
     * 设置字体大小
     *
     * @param size
     */
    public TitleView setTitleSize(int size) {
        if (mTitle != null) {
            mTitle.setTextSize(size);
        }
        return this;
    }

    /**
     * 设置字体颜色
     * @param color
     * @return
     */
    public TitleView setTitleColor(int color) {
        if (mTitle != null) {
            mTitle.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置标题背景
     * @param color
     * @return
     */
    public TitleView setViewBackground(int color){
        mContainer.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题在Banner的位置
     * @param gravity
     * @return
     */
    public TitleView setPosition(Gravity gravity) {
        this.gravity = gravity;
        return this;
    }

    public TitleView setTitleMargin(int left, int top, int right, int bottom) {
        this.marginTop = top;
        this.marginBottom = bottom;
        this.marginLeft = left;
        this.marginRight = right;
        return this;
    }

    /**
     * 设置标题的padding值
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public TitleView setTitlePadding(int left, int top, int right, int bottom) {
        if (mTitle != null) {
            mTitle.setPadding(change2Dp(left), change2Dp(top), change2Dp(right), change2Dp(bottom));
        }
        return this;
    }

    private int change2Dp(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics());
    }
}
