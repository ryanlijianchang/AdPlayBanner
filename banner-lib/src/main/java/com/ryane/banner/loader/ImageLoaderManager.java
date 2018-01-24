package com.ryane.banner.loader;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Create Time: 2017/6/19.
 * @author RyanLee
 */

public class ImageLoaderManager {
    /**
     * 图片加载方式，默认Fresco
     */
    private AdPlayBanner.ImageLoaderType mImageLoaderType = AdPlayBanner.ImageLoaderType.FRESCO;

    /**
     * 点击监听
     */
    private AdPlayBanner.OnPageClickListener mOnPageClickListener = null;

    /**
     * ScaleType，默认是FIT_XY
     */
    private AdPlayBanner.ScaleType mScaleType = AdPlayBanner.ScaleType.FIT_XY;

    /**
     * 图片的缓存集合
     */
    private final ArrayList<Object> mViewCaches = new ArrayList<>();

    private static class SingletonHolder {
        private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();
    }

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 设置图片加载方式
     * TYPE_FRESCO : 使用Facebook的fresco来加载图片
     *
     * @param type ScaleType
     */
    public void setImageLoaderType(AdPlayBanner.ImageLoaderType type) {
        mImageLoaderType = type;
    }

    /**
     * 设置图片显示方式
     * @param mScaleType ScaleTYpe
     */
    public void setScaleType(AdPlayBanner.ScaleType mScaleType) {
        this.mScaleType = mScaleType;
    }

    /**
     * 初始化页面的View
     * @param container 父布局
     * @param context   上下文
     * @param info      数据
     * @param position  位置
     * @return PageView
     */
    public Object initPageView(ViewGroup container, Context context, final AdPageInfo info, final int position) {
        if (container == null || context == null || info == null || position < 0) {
            return null;
        }

        Uri uri = Uri.parse(info.getPicUrl());
        switch (mImageLoaderType) {
            //  默认使用fresco
            default:
            case FRESCO:
                final SimpleDraweeView mFrescoView;
                // 从缓存中拿到View，如果有就直接拿来用
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
                Glide.with((AppCompatActivity) context).load(info.getPicUrl()).into(mGlideView);
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
                Picasso.with(context).load(info.getPicUrl()).into(mPicassoView);
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

    /**
     * 从container中移除已经添加过的view
     * @param container  父布局
     * @param object     不需要用的view
     */
    public void destroyPageView(ViewGroup container, Object object) {
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

    /**
     * 设置页面点击事件
     * @param listener 点击事件监听器
     */
    public void setOnPageClickListener(AdPlayBanner.OnPageClickListener listener) {
        mOnPageClickListener = listener;
    }

    /**
     * Fresco设置ScaleType
     * @param context     上下文
     * @param mFrescoView 图片
     */
    private void frescoViewSetScaleType(Context context, SimpleDraweeView mFrescoView){
        if (context == null || mFrescoView == null) {
            return;
        }
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        mFrescoView.setHierarchy(hierarchy);
        switch (mScaleType) {
            default:
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

    /**
     * Glide和Picasso设置ScaleType
     * @param imageView 传入的ImageView
     */
    private void imageViewSetScaleType(ImageView imageView){
        if (imageView == null) {
            return;
        }
        switch (mScaleType) {
            default:
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
