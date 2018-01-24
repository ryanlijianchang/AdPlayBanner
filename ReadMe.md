# AdPlayBanner #

[AdPlayBanner](https://github.com/ryanlijianchang/AdPlayBanner)是一个Android平台基于ViewPager实现的轮播图插件，主要用以自动或者手动地播放轮播图，提供了Fresco、Picasso、Glide等图片加载方式供用户使用，以及多种图片切换动画，设置轮播时间，设置数据源顺序，提供不同分页指示器等功能，实现了一键式、灵活式的控件使用方式。下面是效果图：

![](http://osjnd854m.bkt.clouddn.com/banner_pic1.gif)

目前AdPlayBanner已经开源到了[Github](https://github.com/ryanlijianchang/AdPlayBanner)上面，大家可以在Github上面查看本控件的Demo，或者直接使用。

如果你想了解AdPlayBanner的实现思路，可以移步到[《手把手、脑把脑教你实现一个无限循环的轮播控件》](https://juejin.im/post/595cc6115188250d7e732180)，里面已经把实现过程告诉大家，或者可以下载demo来自行理解。

## 一、使用方法 ##

### 1.添加依赖 ###

#### bintray依赖 ####

如果使用bintray依赖，直接在模块目录下的`build.gradle`文件添加依赖：

	dependencies {
		  compile 'com.ryane.banner:banner-lib:0.5'
	}

#### jitpack依赖 ####

如果使用jitpack依赖，会需要操作多一步骤。


首先，在项目级别的`build.gradle`文件添加依赖：

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

然后，在模块目录下的`build.gradle`文件添加依赖：

    dependencies {
		  compile 'com.github.ryanlijianchang:AdPlayBanner:v0.5'
	}

### 2.在布局文件中添加控件 ###

在布局文件中添加AdPlayBanner控件，根据自己的需要设置高度，注意，控件需要在一个布局（可以是LinearLayout，RelativeLayout，FrameLayout等）之内。

    <com.ryane.banner.AdPlayBanner
        android:id="@+id/game_banner"
        android:layout_width="match_parent"
        android:layout_height="200dp" />

### 3.在Activity中绑定控件 ###
	
	mAdPlayBanner = (AdPlayBanner) findViewById(R.id.game_banner);

### 4.添加网络权限 ###

由于加载的是网络图片，所以需要在Manifests文件中添加网络请求权限 
	
	<uses-permission android:name="android.permission.INTERNET" />

### 5.初始化Fresco ###

默认使用Fresco加载图片，所以按照Fresco官方做法，需要在Application创建时初始化Fresco，常规做法如下：

(1) 创建MyApplication类继承Application，然后在onCreate()方法中初始化Fresco：

    public class MyApplication extends Application {
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        Fresco.initialize(this);
	    }
	}

(2) 在Manifests文件中<application>标签中增加 `android:name="你的类名"`:

	<application
        android:name=".MyApplication"
		...>

		...

    </application>

当然，如果你不使用Fresco加载，以上步骤可以跳过。

### 6.一键式使用 ###
	
AdPlayBanner使用了Builder设计模式，所以可以通过一键式写法来直接装载AdPlayBanner，当然也可以使用常规写法。在使用之前需要先设置数据源，使用ArrayList<AdPageInfo>来封装数据，而AdPageInfo是一个封装好的Bean类，封装如下：

	public class AdPageInfo implements Parcelable {
	    public String title;    // 广告标题
	    public String picUrl;   // 广告图片url
	    public String clickUlr; // 图片点击url
	    public int order;       // 顺序
	}

在设置完数据源`mDatas`之后，使用Builder模式写法，一句话就可以使用AdPlayBanner了。**注意，`setUp()`方法必须在调用完所有API之后，最后调用**：

	mAdPlayBanner.setInfoList(mDatas).setUp();

当然，你也可以使用常规写法：

	mAdPlayBanner.setInfoList(mDatas);
	mAdPlayBanner.setUp();

### 7.关闭AdPlayBanner ###

在显示AdPlayBanner的页面生命周期到达onDestroy()时，建议调用`mAdPlayBanner.stop()`方法结束AdPlayBanner，避免内存泄漏。

----------

## 二、功能介绍 ##

### 1.自定义数据顺序 ###

在调用`setUp()`方法之前，我们需要设置数据源，每一页的数据使用`AdPageInfo`来封装，它里面就有一个int型变量`order`,我们通过给每一个`AdPageInfo`赋值order，AdPlayBanner就会**自动**按照order的大小来排序，如：

我们封装了三个数据到ArrayList里面，按顺序是图片1->图片2->图片3

	List<AdPageInfo> mDatas = new ArrayList<>();
	AdPageInfo info1 = new AdPageInfo("图片1", "http://osjnd854m.bkt.clouddn.com/pic1_meitu_1.jpg", "链接1", 3);
    AdPageInfo info2 = new AdPageInfo("图片2", "http://osjnd854m.bkt.clouddn.com/pic1_meitu_2.jpg", "链接2", 2);
    AdPageInfo info3 = new AdPageInfo("图片3", "http://osjnd854m.bkt.clouddn.com/pic1_meitu_3.jpg", "链接3", 1);
    mDatas.add(info1);
    mDatas.add(info2);
    mDatas.add(info3);

装载之后运行，可见轮播顺序是按照order的顺序来播放：

![](http://osjnd854m.bkt.clouddn.com/order.gif)

### 2.无限循环轮播 ###

很多轮播插件没有实现无限循环轮播这个功能, 而在AdPlayBanner上得到了实现，如下图可见，当我们无限循环滑动时，插件仍能正常运行：

![](http://osjnd854m.bkt.clouddn.com/pic_circle_play_new.gif)

### 3.支持三种图片加载方式 ###

目前比较主流的Fresco、Picasso、Glide三种图片加载方式在AdPlayBanner中都支持，至于三者的区别我就不赘述了，默认是使用Fresco方式加载，具体调用方法`setImageLoadType(ImageLoaderType type)`，只需要将传入数据设置为：`FRESCO`、`GLIDE`、`PICASSO`其中一种即可，同样，也是可以通过代码一键式使用，例如使用Glide方式加载（其他加载方式使用类似），使用方法如下：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setImageLoadType(Glide)	// 设置Glide类型的图片加载方式
            .setUp();

### 4.支持多种ScaleType ###

在AdPlayBanner中，可以根据用户需要设置图片的ScaleType，具体效果和ImageView的ScaleType一致，默认是使用`FIT_XY`，但是在AdPlayBanner中比ImageView少了一种`MATRIX`类型，在AdPlayBanner中具体支持的ScaleType有如下：`FIT_XY`、`FIT_START`、`FIT_CENTER`、`FIT_END`、`CENTER`、`CENTER_CROP`、`CENTER_INSIDE`其中，具体调用方法`setImageViewScaleType(ScaleType scaleType)`，只需要将具体的ScaleType传入即可，同样，也是可以通过代码一键式使用，例如设置ScaleType为`FIT_START`（其他类似），使用方法如下：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setImageViewScaleType(FIT_START)	// 设置FIT_START类型的ScaleType
            .setUp();

### 5.支持不同页码指示器 ###

在AdPlayBanner中，提供了`数字型`、`点型`和`空型`页码指示器，用户可以通过调用`setIndicatorType(IndicatorType type)`，传入`NONE_INDICATOR`，`NUMBER_INDICATOR`，`POINT_INDICATOR`其中一种，即可显示对应的页码指示器，三种页码指示器对应效果如下：

(1) `NONE_INDICATOR`：空型页码指示器

![](http://osjnd854m.bkt.clouddn.com/indicator_none.jpg)

(2) `NUMBER_INDICATOR`：数字页码指示器

![](http://osjnd854m.bkt.clouddn.com/indicator_number.jpg)

(3) `POINT_INDICATOR`：点型页码指示器

![](http://osjnd854m.bkt.clouddn.com/indicator_point.jpg)

使用方法也是非常简单，如我需要使用数字型页码指示器，使用方法如下：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setIndicatorType(NUMBER_INDICATOR)		//使用数字页码指示器
            .setUp();

此外，你也可以调用`setNumberViewColor(int normalColor, int selectedColor, int numberColor)`来修改数字型页码指示器的样式，`normalColor`为数字没选中时的背景颜色，`selectedColor`为数字选中时的背景颜色，`numberColor`为数字的字体颜色，例如我通过调用这个方法，把三个颜色都改变掉（注意：传入int型颜色必须ARGB8888的颜色类型，或者通过资源文件定义颜色再获取才有效），使用方法如下：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setIndicatorType(NUMBER_INDICATOR)     //使用数字页码指示器
            .setNumberViewColor(0xff00ffff, 0xffff3333, 0xff0000ff)
            .setUp();

得到如下效果：

![](http://osjnd854m.bkt.clouddn.com/numberview_color.jpg)

### 6.添加灵活性标题 ###

在AdPlayBanner中，只需要调用`addTitleView(TitleView mTitleView)`，就可以插入标题了，并且该标题的灵活性很强，可以根据用户需要修改标题的背景、位置、字体大小、padding、magin等，先上一个提供的默认效果：

![](http://osjnd854m.bkt.clouddn.com/pic_title_view_little.gif)

由于是使用了默认的效果，所以用法也是非常简单：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setIndicatorType(POINT_INDICATOR)     //使用数字页码指示器
            .addTitleView(TitleView.getDefaultTitleView(getApplicationContext()))  // 使用默认标题
            .setUp();

我们可以看到我们通过调用`addTitleView(TitleView mTitleView)`，传入一个TitleView即可以生成标题，而上面是调用了AdPlayBanner提供的一个默认标题，当然，我们也说了这个标题的灵活性很强，就是我们可以设置TitleView的属性，我们来看一下TitleView提供了哪些API：

- `TitleView setTitleColor(int color)`：设置标题字体颜色，传入color必须ARGB8888的颜色类型，或者通过资源文件定义颜色再获取才有效。
- `setPosition(Gravity gravity)`：设置标题在AdPlayBanner中的位置，有`PARENT_TOP`,`PARENT_BOTTOM`,`PARENT_CENTER`三个取值，分别位于父布局顶部，父布局底部，父布局中间。
- `setViewBackground(int color)`：设置标题的背景颜色，传入int型颜色必须ARGB8888的颜色类型，或者通过资源文件定义颜色再获取才有效。
- `TitleView setTitleSize(int size)`：设置标题的字体大小，单位是sp。
- `setTitleMargin(int left, int top, int right, int bottom)`：设置标题的四个方向margin值，单位是dp。
- `setTitlePadding(int left, int top, int right, int bottom)`：设置标题的四个方向padding值，单位是dp。

同样，TitleView也是支持Builder模式的写法，例如我自定义一个TitleVIew并加到AdPlayBanner中，使用方法如下：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setIndicatorType(POINT_INDICATOR)     // 使用数字页码指示器
            .addTitleView(new TitleView(getApplicationContext())    // 创建新的TitleView
                                .setPosition(PARENT_TOP)
                                .setTitleColor(0xffffffff)          // 设置字体颜色
                                .setViewBackground(0x9944ff18)      // 设置标题背景颜色
                                .setTitleSize(18)                   // 设置字体大小
                                .setTitleMargin(0,0,2,0)           // 设置margin值
                                .setTitlePadding(2,2,2,2))          // 设置padding值
            .setUp();

效果如下：

![](http://osjnd854m.bkt.clouddn.com/pic_title_view_zidingyi%20.gif)


### 7.支持多样式切换动画 ###

由于AdPlayBanner是基于ViewPager实现的，所以AdPlayBanner和ViewPager一样，同样支持自定义的切换动画，只需要通过调用`setPageTransformer(ViewPager.PageTransformer transformer)`方法，传入一个PageTransformer，即可改变它的切换样式，AdPlayBanner中提供了三种现成的切换方式：

- `FadeInFadeOutTransformer`：淡入淡出效果

![](http://osjnd854m.bkt.clouddn.com/fade_in.gif)

- `RotateDownTransformer`：旋转效果

![](http://osjnd854m.bkt.clouddn.com/rotate.gif)

- `ZoomOutPageTransformer`： 空间切换效果

![](http://osjnd854m.bkt.clouddn.com/zoom_out.gif)

使用起来也是非常简单，例如使用ZoomOutPageTransformer切换效果：

    mAdPlayBanner
            .setInfoList((ArrayList<AdPageInfo>) mDatas)
            .setIndicatorType(POINT_INDICATOR)     // 使用数字页码指示器
            .setBannerBackground(0xff000000)       // 设置背景颜色
            .setPageTransformer(new ZoomOutPageTransformer())   // 设置切换效果
            .setUp();

当然，你也可以自定义一个transformer实现ViewPager.PageTransformer接口，并重写`transformPage(View view, float position)`方法即可实现自定义的切换效果。

### 8.设置是否自动轮播 ###

通过调用`setAutoPlay(boolean autoPlay)`,传入boolean值控制是否自动播放的开关，传入true为自动，传入false为手动。

### 9.设置是否可以手动滑动 ###

通过调用`setCanScroll(boolean canScroll)`,传入boolean值控制是否可以手动滑动，传入true为可以，传入false为不可以。

### 10.设置自动滑动间隔时间 ###

通过调用`setInterval(int interval)`，传入int型的时间（单位ms），即可改变AdPlayBanner自动轮播时的切换时间。

### 11.设置点击事件监听器 ###

AdPlayBanner支持点击事件监听，通过调用`setOnPageClickListener(OnPageClickListener l) `，传入OnPageClickListener，即可完成AdPlayBanner的点击监听，使用方法非常简单：

    mAdPlayBanner
        .setInfoList((ArrayList<AdPageInfo>) mDatas)
        .setIndicatorType(POINT_INDICATOR)     // 使用数字页码指示器
        .setOnPageClickListener(new AdPlayBanner.OnPageClickListener() {
            @Override
            public void onPageClick(AdPageInfo info, int postion) {
                // 点击操作
            }
        })
        .setUp();

### 12.关闭AdPlayBanner ###

在离开显示AdPlayBanner的页面时，建议调用`stop()`方法，避免内存泄漏。

	@Override
    protected void onDestroy() {
        if (mAdPlayBanner != null) {
            // 结束时需要调用stop释放资源
            mAdPlayBanner.stop();
        }
        super.onDestroy();
    }

## 三、API ##

**AdPlayBanner：实现轮播效果的控件**

AdPlayBanner | 解释 | 备注
----|------|----
addTitleView(TitleView mTitleView) | 添加一个TitleView  | 可以通过TitleView.getDefaultTitleView(Context context)来使用默认的TitleView或者通过new Title()的方式传入
setBannerBackground(int color) | 设置AdPlayBanner的背景颜色  | 传入color必须ARGB8888的颜色类型，或者通过资源文件定义颜色再获取才有效
setIndicatorType(IndicatorType type) | 设置页码指示器类型  | 传入`NONE_INDICATOR`，`NUMBER_INDICATOR`，`POINT_INDICATOR`其中一种
setInterval(int interval) | 设置自动轮播时的切换时间 | 单位ms
setImageLoadType(ImageLoaderType type) | 设置图片加载方式 | 传入`FRESCO`、`GLIDE`、`PICASSO`其中一种
setPageTransformer(ViewPager.PageTransformer transformer) | 设置切换动画，如果不设置动画，设置为null | 提供了`FadeInFadeOutTransformer`，`RotateDownTransformer`，`ZoomOutPageTransformer`三种，也可以传入自定义的TransFormer
setNumberViewColor(int normalColor, int selectedColor, int numberColor) | 设置数字页码的颜色 | normalColor   数字正常背景颜色，selectedColor 数字选中背景颜色，numberColor   数字字体颜色
setOnPageClickListener(OnPageClickListener l) | 设置事件点击监听器 | 传入一个OnPageClickListener
setImageViewScaleType(ScaleType scaleType) | 设置图片的ScaleType | 传入`FIT_XY`、`FIT_START`、`FIT_CENTER`、`FIT_END`、`CENTER`、`CENTER_CROP`、`CENTER_INSIDE`其中一种
setAutoPlay(boolean autoPlay) | 设置是否自动播放 | 默认为true 自动播放，传入false为手动
setCanScroll(boolean canScroll) | 设置是否可以手动滑动 | 默认为true可以，传入false为不可以
setInfoList(ArrayList<AdPageInfo> pageInfos) | 设置Banner的数据源 | 传入必须为AdPageInfo类型的ArrayList
setUp() | 装载AdPlayBanner | 必须在以上所有方法调用完之后才能调用
stop()  | 结束AdPlayBanner | 在离开显示AdPlayBanner页面时调用，避免内存泄漏

**TitleView ： 标题控件**

TitleView | 解释 | 备注
----|------|----
getDefaultTitleView(Context context) | 获取一个默认的TitleView | 传入一个Context
setTitleSize(int size) | 设置字体大小 | 单位sp
setTitleColor(int color) | 设置字体颜色 | 传入color必须ARGB8888的颜色类型，或者通过资源文件定义颜色再获取才有效
setViewBackground(int color) | 设置标题背景 | 传入color必须ARGB8888的颜色类型，或者通过资源文件定义颜色再获取才有效
setPosition(Gravity gravity) | 设置标题在Banner的位置 | 只能`PARENT_TOP`,`PARENT_BOTTOM`,`PARENT_CENTER`其中一个值
setTitleMargin(int left, int top, int right, int bottom) | 设置标题的margin值 | 单位dp
setTitlePadding(int left, int top, int right, int bottom) | 设置标题的padding值 | 单位dp

**AdPageInfo：AdPlayView指定的数据源**

AdPageInfo | 解释 | 备注
----|------|----
AdPageInfo(String title, String picUrl, String clickUlr, int order) | 构造方法 | 
void setTitle(String title) | 设置标题 | 
String getTitle() | 获取标题 |
void setPicUrl(String picUrl) | 设置图片源地址 | 
String getPicUrl() | 获取图片链接 |
void setClickUlr(String clickUlr) | 设置点击事件地址 | 
String getClickUlr() | 获取点击事件链接 |
void setOrder(int order) | 设置排序的优先级 | 设置了order，AdPlayBanner会根据order的大小由小到大排序
int getOrder() | 获取排序优先级 |

## 四、版本特性 ##

### V0.5 ###

1. 增加`setCanScroll(boolean canScroll)`接口控制是否可以手动滑动。

### V0.4 ###

1. FIX BUG 多次刷新时数据源为空时造成的崩溃。
2. 代码、布局、性能优化。
3. gradle升级。
4. Fresco升级至V1.8(支持GIF、WebP)；Picasso版本升级至V2.5.2；Glide是V4.0.0。


### V0.3 ###

1. 修复了静态变量造成的内存泄漏问题;
2. 提供手动结束Banner播放的接口;

### V0.2 ###

1. 支持定义数据顺序；
2. 无限循环轮播；
3. 支持Fresco、Glide、Picasso三种图片加载方式；
4. 支持多种ScaleType；
5. 支持点型、数字型、空型页码指示器；支持修改数字型页码器的样式；
6. 支持灵活性标题；支持修改标题的位置、字体大小、颜色、边距值等属性；
7. 支持多样式切换动画；
8. 支持设置自动轮播开关；
9. 自定义自动滑动间隔时间；
10. 提供点击事件监听器；
11. 支持修改AdPlayBanner的背景颜色；

### v0.1 ###

1. 基本框架搭建完成；

## 五、 Demo ##

如果大家在使用在仍然有问题，可以通过下载[Demo](https://github.com/ryanlijianchang/AdPlayBanner)来学习，当然，大家更可以通过查看源代码来学习如何自定义一个轮播控件。

## 六、后记 ##

AdPlayBanner作为作者的第一个开源控件，作者也是非常用心认真地完成，这个过程也学习到很多东西，可能其中会遇到很多错误，所以希望大家可以多多包涵，然后把错误提到Issues里面，作者会在看到的第一时间进行修正。在后面的时间里，作者也会将更多的特性加到这个控件里面，所以希望大家可以加个star，以作为对作者的小小鼓励。 当然，如果你想第一时间联系到作者，不妨尝试以下联系方式：


- Email：liji.anchang@163.com 
- CSDN：[http://blog.csdn.net/ljcitworld](http://blog.csdn.net/ljcitworld)
- Github：[https://github.com/ryanlijianchang](https://github.com/ryanlijianchang)








