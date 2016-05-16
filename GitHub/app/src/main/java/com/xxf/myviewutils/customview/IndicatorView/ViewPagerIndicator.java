package com.xxf.myviewutils.customview.IndicatorView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxf.myviewutils.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ViewPagerIndicator extends LinearLayout {


    private Paint mPaint;//画笔

    private Path mPath;//三角形，三条边构成的路线

    private int mTriangleWidth;//三角形宽度

    private int mTriangleHeight;//三角形高度

    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;//三角形的宽度和每一个TAB跨度的比例《可以分享出去，让用户自定，可适应不同比例，不同屏幕》
    /**
     * 设置三角形最大为三分之一屏幕的六分之一
     */
    private  final int DIMENSION_TRIANGLE_WDTH_MAX= (int) ((getScreenWidth()/3)*RADIO_TRIANGLE_WIDTH);

    private int mInitTranslationX;//初始化的偏移位置

    protected int mTranslationX;//移动时的偏移位置,根据滑动的时候移动的偏移量

    protected int mTabVisableCount;//设置屏幕上可以显示几个tab

    private static final int COUNT_DEFFAULT_TAB = 4;//设置tab默认显示数量
    private  List<String> mTitles;
    private static  final int COLOR_TEXT_NORMAL=0x77FFFFFF;
    //设置选择文字高亮
    private static final int COLOR_TEXT_HIGHLIGHT = 0xFFFFFFFF;
    private int screenWidth;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }


    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取可见tab的数量
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisableCount = a.getInt(R.styleable.ViewPagerIndicator_visable_tab_count, COUNT_DEFFAULT_TAB);

        if (mTabVisableCount < 0) {//当用户设置显示数量小于0时设为默认值，或者抛出异常都可以
            mTabVisableCount = COUNT_DEFFAULT_TAB;
        }

        a.recycle();//释放
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setColor(Color.parseColor("#ffffff"));//设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为全部填充
        mPaint.setPathEffect(new CornerPathEffect(3));//设置三角形转角点弧度为3

    }

    /**
     * 绘制自己的子控件用这个，绘制布局用OnDraw
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {//画三角形
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        super.dispatchDraw(canvas);
    }

    //宽高发生变化时候都会回调
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int) (getScreenWidth() / mTabVisableCount * RADIO_TRIANGLE_WIDTH);//获取到三角形的宽度

        mTriangleWidth=Math.min(mTriangleWidth,DIMENSION_TRIANGLE_WDTH_MAX);

        mInitTranslationX = getScreenWidth() / mTabVisableCount / 2 - mTriangleWidth / 2;//三角形初始偏移的位置
        Log.i("INF",getScreenWidth()+"=="+mTabVisableCount+"==="+mTriangleWidth+"==="+mTabVisableCount+"===");
        initTrangle();//初始化
    }

    /**
     * 当xml加载完成会回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int cCount = getChildCount();//获取到子元素的数量
        if (cCount == 0) return;

        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            //这里tab的宽度会根据需要显示的tab个数进行设置
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            //设置权重，均分屏幕，getScreenWidth()，获取到屏幕的宽度
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisableCount;
            view.setLayoutParams(lp);//将子控件属性设置进去
        }
        setItemClickEvent();
    }

    /**
     * 初始化三角形
     */
    private void initTrangle() {
        mTriangleHeight = mTriangleWidth / 2;//定义三角形的高度为宽度的1/2
        mPath = new Path();//初始化Path类
        mPath.moveTo(0, 0);//初始化移动到0，0位置
        mPath.lineTo(mTriangleWidth, 0);//第二个点
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);//第三个点，y向下为正，添加负号
        mPath.close();//闭合，三角形完成

    }



    public void setTabItemTitles(List<String> titles){
        if(titles!=null&&titles.size()>0){
            this.removeAllViews();
            mTitles=titles;
            for(String title:mTitles){
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }

    }



    /***
     * 动态设置可以显示的tab数量---这句话一定要在设置setTabItemTitles之前设置
     * @param count
     */
    public void setVisibleTabCount(int count){
        mTabVisableCount=count;
    }


    /**
     * 根据title创建Tab
     * @param title
     * @return
     */
    private View generateTextView(String title) {
        TextView tv=new TextView(getContext());
        LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width=getScreenWidth()/mTabVisableCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    private ViewPager mViewPager;


    /**
     * 设置ViewPager
     * @param viewPager
     * @param pos
     */
    public void setViewPager(ViewPager viewPager,int pos){
        mViewPager=viewPager;
    }

    /**
     * 获取到屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMe=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMe);
        return outMe.widthPixels;
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor(){
        for(int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                ((TextView)view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    /**
     * 高亮某个Tab的文本
     * @param pos
     */
    public  void highLightTextView(int pos){
        resetTextViewColor();
        View view = getChildAt(pos);
        if(view instanceof TextView){
            ((TextView)view).setTextColor(COLOR_TEXT_HIGHLIGHT);
        }
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent(){
        int cCount=getChildCount();
        for( int i=0;i<cCount;i++){
            final int j=i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }
    //指示器跟随手机进行移动
    public void scroll(int position, float positionOffset) {
        int tabWidth =getScreenWidth() /mTabVisableCount;
        mTranslationX = (int) (tabWidth * (positionOffset + position));
        Log.i("inf", tabWidth + "==" + mTranslationX + "==" + positionOffset);
        //容器移动，当tab处于移动的最后一个时候
        if(position>=(mTabVisableCount-1)&&positionOffset>0&&getChildCount()>mTabVisableCount){
            //  mHScrollView.smoothScrollTo((position-(mTabVisableCount-1))*tabWidth+(int)(tabWidth*positionOffset),0);
           // this.scrollTo((position-(mTabVisableCount-1))*tabWidth+(int)(tabWidth*positionOffset),0);
        }

        invalidate();//便宜完成调用重回方法

    }
}
