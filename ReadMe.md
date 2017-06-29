# AdPlayBanner #

AdPlayBanner是一个Android平台的轮播图插件，主要用以自动或者手动地播放轮播图，提供了Fresco、Picasso、Glide等图片加载方式供用户使用，以及多种图片切换动画，设置轮播时间等功能，实现了傻瓜式的控价使用方式。

## 一、使用方法 ##

### 1.添加依赖 ###

首先，在项目级别的`build.gradle`文件添加依赖：

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

然后，在模块目录下的`build.gradle`文件添加依赖：

    dependencies {
		compile 'com.github.ryanlijianchang:AdPlayBanner:v0.1'
	}

### 2.在布局文件中添加控件 ###

高度可以根据自己的需要设置，控件需要在一个布局（可以是LinearLayout，RelativeLayout，FrameLayout等）之内。

        <com.ryane.banner_lib.AdPlayView
        	android:id="@+id/game_banner"
        	android:layout_width="match_parent"
        	android:layout_height="200dp" />

### 3.在Activity中绑定控件 ###

	mAdPlayView = (AdPlayView) findViewById(R.id.game_banner);

### 4.根据需要调用api ###

    mAdPlayView.setInfoList(mDatas);    // 设置数据源，传入数据格式为List<AdPageInfo>
    mAdPlayView.setInterval(2000);      // 设置间隔时间，单位ms
    mAdPlayView.setBackgroundColor(Color.parseColor("#000000"));    // 设置图片背景颜色
    mAdPlayView.setImageLoadType(ImageLoaderManager.TYPE_FRESCO);   // 设置图片加载方式，默认Fresco
    mAdPlayView.setPageTransfromer(TransfromerManager.TRANSFORMER_ZOOM_OUT_PAGE);   // 设置图片切换动画
    mAdPlayView.setUp();    // 装载AdPlayView



## 二、支持三种图片加载方式 ##

目前比较主流的Fresco、Picasso、Glide三种图片加载方式在AdPlayBanner中都支持，至于三者的区别我就不赘述了，使用方法是

    mAdPlayView.setImageLoadType(ImageLoaderManager.TYPE_FRESCO);
	mAdPlayView.setImageLoadType(ImageLoaderManager.TYPE_PICASSO);
	mAdPlayView.setImageLoadType(ImageLoaderManager.TYPE_GLIDE);

## 三、支持多种图片切换动画 ##

使用方法：

	mAdPlayView.setPageTransfromer(TransfromerManager.TRANSFORMER_ZOOM_OUT_PAGE);   // 设置图片切换动画

所有动画通过`TransfromerManager`来控制，通过 `mAdPlayView.setPageTransfromer`传入，如下为`TransfromerManager`的动画列表：

    public class TransfromerManager {
	    public static final int TRANSFORMER_DEFAULT = 0;
	    public static final int TRANSFORMER_DEPTH_PAGE = 1;
	    public static final int TRANSFORMER_ZOOM_OUT_PAGE = 2;
	    public static final int TRANSFORMER_ROTATE_DOWN = 3;
	}
