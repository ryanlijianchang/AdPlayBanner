package com.ryane.banner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/17.
 * Email: lijianchang@yy.com
 */

public class AdPageInfo implements Parcelable {
    public String title;    // 广告标题
    public String picUrl;   // 广告图片url
    public String clickUlr; // 图片点击url
    public int order;       // 顺序

    public AdPageInfo(String title, String picUrl, String clickUlr, int order) {
        this.title = title;
        this.picUrl = picUrl;
        this.clickUlr = clickUlr;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getClickUlr() {
        return clickUlr;
    }

    public int getOrder() {
        return order;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setClickUlr(String clickUlr) {
        this.clickUlr = clickUlr;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "AdPageInfo{" +
                "title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", clickUlr='" + clickUlr + '\'' +
                ", order=" + order +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.picUrl);
        dest.writeString(this.clickUlr);
        dest.writeInt(this.order);
    }

    protected AdPageInfo(Parcel in) {
        this.title = in.readString();
        this.picUrl = in.readString();
        this.clickUlr = in.readString();
        this.order = in.readInt();
    }

    public static final Creator<AdPageInfo> CREATOR = new Creator<AdPageInfo>() {
        @Override
        public AdPageInfo createFromParcel(Parcel source) {
            return new AdPageInfo(source);
        }

        @Override
        public AdPageInfo[] newArray(int size) {
            return new AdPageInfo[size];
        }
    };
}
