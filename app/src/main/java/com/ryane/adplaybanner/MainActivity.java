package com.ryane.adplaybanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.ryane.banner.transformer.FadeInFadeOutTransformer;
import com.ryane.banner.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner.AdPlayBanner.IndicatorType.POINT_INDICATOR;
import static com.ryane.banner.view.TitleView.Gravity.PARENT_TOP;

public class MainActivity extends AppCompatActivity {
    private AdPlayBanner mAdPlayBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AdPageInfo> mDatas = new ArrayList<>();
        AdPageInfo info1 = new AdPageInfo("拜仁球场冠绝全球", "http://onq81n53u.bkt.clouddn.com/photo1.jpg", "http://www.bairen.com", 1);
        AdPageInfo info2 = new AdPageInfo("日落东单一起战斗", "http://onq81n53u.bkt.clouddn.com/photo2.jpg", "http://www.riluodongdan.com", 4);
        AdPageInfo info3 = new AdPageInfo("香港夜景流连忘返", "http://onq81n53u.bkt.clouddn.com/photo3333.jpg", "http://www.hongkong.com", 2);
        AdPageInfo info4 = new AdPageInfo("耐克大法绝顶天下", "http://7xrwkh.com1.z0.glb.clouddn.com/1.jpg", "http://www.nike.com", 3);

        mDatas.add(info1);
        mDatas.add(info2);
        mDatas.add(info3);
        mDatas.add(info4);

        mAdPlayBanner = (AdPlayBanner) findViewById(R.id.game_banner);


        mAdPlayBanner
                .setImageLoadType(GLIDE)
                .setOnPageClickListener(new AdPlayBanner.OnPageClickListener() {
                    @Override
                    public void onPageClick(AdPageInfo info, int postion) {
                        Toast.makeText(getApplicationContext(), "你点击了图片 " + info.getTitle() + "\n 跳转链接为：" + info.getClickUlr() + "\n 当前位置是：" + postion +"\n 当前优先级是：" + info.getOrder(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setAutoPlay(true)
                .setIndicatorType(POINT_INDICATOR)
                .setNumberViewColor(0xcc00A600, 0xccea0000, 0xffffffff)
                .setInterval(3000)
                .addTitleView(new TitleView(this).setPosition(PARENT_TOP).setTitlePadding(5, 5, 5, 5).setTitleMargin(0, 0, 0, 25).setTitleSize(16).setViewBackground(0x55000000).setTitleColor(getResources().getColor(R.color.white)))
                .setBannerBackground(0xff000000)
                .setPageTransfromer(new FadeInFadeOutTransformer())
                .setInfoList((ArrayList<AdPageInfo>) mDatas)
                .setUp();


    }

}
