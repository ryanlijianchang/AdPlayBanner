package com.ryane.banner_lib.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/30.
 * Email: lijianchang@yy.com
 */

public class PointView extends android.support.v7.widget.AppCompatTextView {
    private float mSize;
    private int mColor = 0xfff44336;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PointView(Context context, float mSize, int mColor) {
        super(context);
        this.mSize = mSize;
        this.mColor = mColor;
        setDefault(mSize);
    }

    private void setDefault(float size) {
        setGravity(Gravity.CENTER);
        setTextColor(Color.WHITE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        if (size <= 0) {
            mSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, metrics);
        } else {
            mSize = size;
        }

        if (metrics.density <= 1.5) {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        }

        int paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics);
        int paddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics);

        setPadding(paddingLeft, 0, paddingRight, 0);

        change();
    }

    private void change() {
        float[] outerR = new float[]{mSize / 2, mSize / 2, mSize / 2, mSize / 2, mSize / 2, mSize / 2, mSize / 2, mSize / 2};
        Shape shape = new RoundRectShape(outerR, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.setIntrinsicHeight((int) mSize);
        shapeDrawable.setIntrinsicWidth((int) mSize);
        shapeDrawable.setPadding(0, 0, 0, 0);
        shapeDrawable.getPaint().setColor(mColor);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        setBackgroundDrawable(shapeDrawable);
        setHeight((int) mSize);
        setMinWidth((int) mSize);
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
        change();
    }
}
