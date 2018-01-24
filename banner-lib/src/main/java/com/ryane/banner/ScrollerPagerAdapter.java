package com.ryane.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ryane.banner.loader.ImageLoaderManager;
import com.ryane.banner.util.ListUtils;

import java.util.List;

/**
 * Create Time: 2017/6/17.
 *
 * @author RyanLee
 */

class ScrollerPagerAdapter extends PagerAdapter {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据源
     */
    private List<AdPageInfo> mDataList;


    ScrollerPagerAdapter(Context context, List<AdPageInfo> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }


    @Override
    public int getCount() {
        if (!ListUtils.isEmpty(mDataList)) {
            // 当只有一张图片的时候，不可滑动
            if (mDataList.size() == 1) {
                return 1;
            } else {
                // 否则循环播放滑动
                return Integer.MAX_VALUE;
            }
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (!ListUtils.isEmpty(mDataList)) {
            return ImageLoaderManager.getInstance().initPageView(container, mContext, mDataList.get(position % mDataList.size()), position % mDataList.size());
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
