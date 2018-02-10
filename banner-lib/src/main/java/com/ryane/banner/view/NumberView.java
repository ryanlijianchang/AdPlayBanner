package com.ryane.banner.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryane.banner.R;

/**
 * Create Time: 2017/7/1.
 * @author RyanLee
 */

public class NumberView extends RelativeLayout {

    /**
     * 数字
     */
    private TextView mTvNumber;

    /**
     * 数字默认颜色
     */
    public static int mNumberViewNormalColor =  0xcc666666;
    /**
     * 数字选中颜色
     */
    public static int mNumberViewSelectedColor = 0xccA500CC;
    /**
     * 数字颜色
     */
    public static int mNumberTextColor = 0xffffffff;


    public NumberView(Context context, int color) {
        this(context);
        setNumberViewColor(color);
    }

    public NumberView(Context context) {
        this(context, null);
    }

    public NumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        LayoutInflater.from(getContext()).inflate(R.layout.view_number, this, true);
        mTvNumber = findViewById(R.id.number);
    }

    public void setNumber(int number) {
        if (mTvNumber != null) {
            String numberStr = number + "";
            mTvNumber.setText(numberStr);
            mTvNumber.setTextColor(mNumberTextColor);
        }
    }

    public void setNumberViewColor(int color) {
        mTvNumber.setBackgroundColor(color);
    }

}
