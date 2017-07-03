package com.ryane.banner_lib.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryane.banner_lib.R;

/**
 * Creator: lijianchang
 * Create Time: 2017/7/1.
 * Email: lijianchang@yy.com
 */

public class NumberView extends RelativeLayout {
    private TextView mTvNumber;

    public static int mNumberViewNormalColor =  0xcc666666;
    public static int mNumberViewSelectedColor = 0xccA500CC;
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
        LayoutInflater.from(getContext()).inflate(R.layout.view_number, this);
        mTvNumber = (TextView) findViewById(R.id.number);
    }

    public void setNumber(int number) {
        if (mTvNumber != null) {
            mTvNumber.setText(number + "");
            mTvNumber.setTextColor(mNumberTextColor);
        }
    }

    public void setNumberViewColor(int color) {
        mTvNumber.setBackgroundColor(color);
    }

}
