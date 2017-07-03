package com.ryane.banner_lib.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryane.banner_lib.R;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/30.
 * Email: lijianchang@yy.com
 */

public class TitleView extends RelativeLayout {
    private TextView mTitle;
    private RelativeLayout mContainer;

    public static final int ALIGN_PARENT_TOP = 0;
    public static final int ALIGN_PARENT_BOTTOM = 1;
    public static final int CENTER_IN_PARENT = 2;

    public int gravity = 1;    // 在父布局中的位置
    public int marginTop, marginBottom, marginLeft, marginRight = 0;

    public TitleView(Context context) {
        this(context, null);
    }

    public static TitleView getDefaultTitleView(Context context){
        TitleView titleView = new TitleView(context);
        titleView.setPosition(ALIGN_PARENT_BOTTOM)
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

    public TitleView setTitleColor(int color) {
        if (mTitle != null) {
            mTitle.setTextColor(color);
        }
        return this;
    }

    public TitleView setViewBackground(int color){
        mContainer.setBackgroundColor(color);
        return this;
    }

    public TitleView setPosition(int gravity) {
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
