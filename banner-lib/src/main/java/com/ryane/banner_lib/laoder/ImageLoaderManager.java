package com.ryane.banner_lib.laoder;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ryane.banner_lib.AdPageInfo;
import com.ryane.banner_lib.AdPlayBanner;
import com.ryane.banner_lib.ScrollerPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/19.
 * Email: lijianchang@yy.com
 */

public class ImageLoaderManager {
    private int mImageLoaderType = 0;  // 图片加载方式，默认0

    public static AdPlayBanner.OnPageClickListener mOnPageClickListener = null;

    public static final int TYPE_FRESCO = 1;    // fresco加载方式
    public static final int TYPE_GLIDE = 2;     // glide加载方式
    public static final int TYPE_PICASSO = 3;   // picasso加载方式

    private final ArrayList<Object> mViewCaches = new ArrayList<>();

    private static class SingletonHolder {
        private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();
    }

    private ImageLoaderManager() {
    }

    public static final ImageLoaderManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 设置图片加载方式
     * TYPE_FRESCO : 使用Facebook的fresco来加载图片
     *
     * @param type
     */
    public void setImageLoaderType(int type) {
        mImageLoaderType = type;
    }

    public Object initPageView(ViewGroup container, Context context, final AdPageInfo info, final int position) {
        Log.d("smzq", "initpageView");
        Log.d("ImageLoaderManager", "position = " + position + "; mViewCaches.size() = " + mViewCaches.size() + "; container.size() = " + container.getChildCount()
                + "; AdPageInfo = " + info.toString());

        Uri uri = Uri.parse(info.picUrl);
        switch (mImageLoaderType) {
            default:
            case TYPE_FRESCO:
                final SimpleDraweeView mFrescoView;
                if (mViewCaches.isEmpty()) {
                    mFrescoView = new SimpleDraweeView(context);
                    mFrescoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mFrescoView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    mFrescoView = (SimpleDraweeView) mViewCaches.remove(0);
                }
                mFrescoView.setImageURI(uri);
                container.addView(mFrescoView);
                mFrescoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnPageClickListener != null) {
                            mOnPageClickListener.onPageClick(info, position);
                        }
                    }
                });
                return mFrescoView;
            case TYPE_GLIDE:
                final ImageView mGlideView;
                if (mViewCaches.isEmpty()) {
                    mGlideView = new ImageView(context);
                    mGlideView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mGlideView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    mGlideView = (ImageView) mViewCaches.remove(0);
                }
                Glide.with((AppCompatActivity) context).load(info.picUrl).into(mGlideView);
                container.addView(mGlideView);
                mGlideView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnPageClickListener != null) {
                            mOnPageClickListener.onPageClick(info, position);
                        }
                    }
                });
                return mGlideView;
            case TYPE_PICASSO:
                final ImageView mPicassoView;
                if (mViewCaches.isEmpty()) {
                    mPicassoView = new ImageView(context);
                    mPicassoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mPicassoView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    mPicassoView = (ImageView) mViewCaches.remove(0);
                }
                Picasso.with(context).load(info.picUrl).into(mPicassoView);
                mPicassoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnPageClickListener != null) {
                            mOnPageClickListener.onPageClick(info, position);
                        }
                    }
                });
                container.addView(mPicassoView);
                return mPicassoView;
        }
    }

    public void destroyPageView(ViewGroup container, Object object) {
        Log.d("smzq", "destroyPageView");
        switch (mImageLoaderType) {
            default:
            case TYPE_FRESCO:
                SimpleDraweeView mFrescoView = (SimpleDraweeView) object;
                container.removeView(mFrescoView);
                mViewCaches.add(mFrescoView);
                break;

            case TYPE_GLIDE:
                ImageView mGlideView = (ImageView) object;
                container.removeView(mGlideView);
                mViewCaches.add(mGlideView);
                break;

            case TYPE_PICASSO:
                ImageView mPicassoView = (ImageView) object;
                container.removeView(mPicassoView);
                mViewCaches.add(mPicassoView);
                break;
        }
    }

    public void setmOnPageClickListener(AdPlayBanner.OnPageClickListener l) {
        mOnPageClickListener = l;
    }
}
