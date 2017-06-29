package com.ryane.adplaybanner;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/19.
 * Email: lijianchang@yy.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}