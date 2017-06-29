package com.ryane.banner_lib;

/**
 * Creator: lijianchang
 * Create Time: 2017/6/17.
 * Email: lijianchang@yy.com
 */

public class AdPageInfo {
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


}
