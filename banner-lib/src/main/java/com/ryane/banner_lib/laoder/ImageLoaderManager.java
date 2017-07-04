package com.ryane.banner_lib.laoder;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ryane.banner_lib.AdPageInfo;
import com.ryane.banner_lib.AdPlayBanner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/19.
 * Email: lijianchang@yy.com
 */

public class ImageLoaderManager {
    private AdPlayBanner.ImageLoaderType mImageLoaderType = AdPlayBanner.ImageLoaderType.FRESCO;  // 图片加载方式，默认0

    public static AdPlayBanner.OnPageClickListener mOnPageClickListener = null;
    private AdPlayBanner.ScaleType mScaleType = AdPlayBanner.ScaleType.FIT_XY;

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
    public void setImageLoaderType(AdPlayBanner.ImageLoaderType type) {
        mImageLoaderType = type;
    }

    /**
     * 设置图片显示方式
     * @param mScaleType
     */
    public void setmScaleType(AdPlayBanner.ScaleType mScaleType) {
        this.mScaleType = mScaleType;
    }

    public AdPlayBanner.ScaleType getScaleType() {
        return mScaleType;
    }

    public Object initPageView(ViewGroup container, Context context, final AdPageInfo info, final int position) {
        Log.d("smzq", "initpageView");
        Log.d("ImageLoaderManager", "position = " + position + "; mViewCaches.size() = " + mViewCaches.size() + "; container.size() = " + container.getChildCount()
                + "; AdPageInfo = " + info.toString());

        Uri uri = Uri.parse(info.picUrl);
        switch (mImageLoaderType) {
            default:
            case FRESCO:
                final SimpleDraweeView mFrescoView;
                if (mViewCaches.isEmpty()) {
                    mFrescoView = new SimpleDraweeView(context);
                    mFrescoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    // 设置ScaleType
                    frescoViewSetScaleType(context, mFrescoView);
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
            case GLIDE:
                final ImageView mGlideView;
                if (mViewCaches.isEmpty()) {
                    mGlideView = new ImageView(context);
                    mGlideView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    imageViewSetScaleType(mGlideView);
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
            case PICASSO:
                final ImageView mPicassoView;
                if (mViewCaches.isEmpty()) {
                    mPicassoView = new ImageView(context);
                    mPicassoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    imageViewSetScaleType(mPicassoView);
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
            case FRESCO:
                SimpleDraweeView mFrescoView = (SimpleDraweeView) object;
                container.removeView(mFrescoView);
                mViewCaches.add(mFrescoView);
                break;

            case GLIDE:
                ImageView mGlideView = (ImageView) object;
                container.removeView(mGlideView);
                mViewCaches.add(mGlideView);
                break;

            case PICASSO:
                ImageView mPicassoView = (ImageView) object;
                container.removeView(mPicassoView);
                mViewCaches.add(mPicassoView);
                break;
        }
    }

    public void setmOnPageClickListener(AdPlayBanner.OnPageClickListener l) {
        mOnPageClickListener = l;
    }

    private void frescoViewSetScaleType(Context context, SimpleDraweeView mFrescoView){
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        mFrescoView.setHierarchy(hierarchy);
        switch (mScaleType) {
            case FIT_XY:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                break;
            case FIT_START:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START);
                break;
            case FIT_CENTER:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                break;
            case FIT_END:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END);
                break;
            case CENTER:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
                break;
            case CENTER_CROP:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                break;
            case CENTER_INSIDE:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
                break;
        }
    }

    private void imageViewSetScaleType(ImageView imageView){
        switch (mScaleType) {
            case FIT_XY:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case FIT_START:
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case FIT_CENTER:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case FIT_END:
                imageView.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case CENTER:
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case CENTER_CROP:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case CENTER_INSIDE:
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
        }
    }

}
