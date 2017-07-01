package com.ryane.adplaybanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryane.banner_lib.AdPageInfo;
import com.ryane.banner_lib.AdPlayBanner;
import com.ryane.banner_lib.view.TitleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdPlayBanner mAdPlayBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AdPageInfo> mDatas = new ArrayList<>();
        AdPageInfo info1 = new AdPageInfo("第一标题：拜仁球场冠绝全球", "http://onq81n53u.bkt.clouddn.com/photo1.jpg", "clickUrl", 1 );
        AdPageInfo info2 = new AdPageInfo("第二标题：日落东单一起战斗", "http://onq81n53u.bkt.clouddn.com/photo2.jpg", "clickUrl", 1 );
        AdPageInfo info3 = new AdPageInfo("第三标题：香港夜景流连忘返", "http://onq81n53u.bkt.clouddn.com/photo3333.jpg", "clickUrl", 1 );
        AdPageInfo info4 = new AdPageInfo("第四标题：耐克大法绝顶天下", "http://7xrwkh.com1.z0.glb.clouddn.com/1.jpg", "clickUrl", 1 );


        mDatas.add(info1);
        mDatas.add(info2);
        mDatas.add(info3);
        mDatas.add(info4);

        mAdPlayBanner = (AdPlayBanner) findViewById(R.id.game_banner);


        mAdPlayBanner
                .setInfoList(mDatas)
                .setUp();
    }

}
