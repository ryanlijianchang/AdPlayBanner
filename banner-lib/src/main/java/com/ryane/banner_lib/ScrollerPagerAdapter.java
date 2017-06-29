package com.ryane.banner_lib;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ryane.banner_lib.laoder.ImageLoaderManager;

import java.util.List;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/17.
 * Email: lijianchang@yy.com
 */

public class ScrollerPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<AdPageInfo> mInfos;


    public ScrollerPagerAdapter(Context context, List<AdPageInfo> infos) {
        this.mContext = context;
        this.mInfos = infos;
    }


    @Override
    public int getCount() {
        if (null != mInfos)
            // 当只有一张图片的时候，不可滑动
            if (mInfos.size() == 1){
                return 1;
            } else {
                // 否则循环播放滑动
                return Integer.MAX_VALUE;
            }
        else
            return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mInfos != null && mInfos.size() > 0) {
            return ImageLoaderManager.getInstance().initPageView(container, mContext, mInfos.get(position % mInfos.size()), position);
        } else {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageLoaderManager.getInstance().destroyPageView(container, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
