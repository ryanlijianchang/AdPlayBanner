package com.ryane.adplaybanner;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ryane.banner_lib.AdPageInfo;
import com.ryane.banner_lib.AdPlayView;
import com.ryane.banner_lib.laoder.ImageLoaderManager;
import com.ryane.banner_lib.transformer.TransfromerManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdPlayView mAdPlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AdPageInfo> mDatas = new ArrayList<>();
        AdPageInfo info1 = new AdPageInfo("title", "http://onq81n53u.bkt.clouddn.com/photo1.jpg", "clickUrl", 1 );
        AdPageInfo info2 = new AdPageInfo("title", "http://onq81n53u.bkt.clouddn.com/photo2.jpg", "clickUrl", 1 );
        AdPageInfo info3 = new AdPageInfo("title", "http://onq81n53u.bkt.clouddn.com/photo3333.jpg", "clickUrl", 1 );
        mDatas.add(info1);
        mDatas.add(info2);
        mDatas.add(info3);

        mAdPlayView = (AdPlayView) findViewById(R.id.game_banner);
        mAdPlayView.setInfoList(mDatas);
        mAdPlayView.setInterval(2000);
        mAdPlayView.setBackgroundColor(Color.parseColor("#000000"));
        mAdPlayView.setImageLoadType(ImageLoaderManager.TYPE_FRESCO);
        mAdPlayView.setPageTransfromer(TransfromerManager.TRANSFORMER_ZOOM_OUT_PAGE);
        mAdPlayView.setUp();
    }
}
