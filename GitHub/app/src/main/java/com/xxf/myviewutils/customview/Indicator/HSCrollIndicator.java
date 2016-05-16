package com.xxf.myviewutils.customview.Indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.xxf.myviewutils.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class HSCrollIndicator extends HorizontalScrollView {
    private ViewPagerIndicator ln;
    public PageOnchangeListener mListener;
    /**
     * 因为我们内部实现了mViewPager.addOnPageChangeListener方法当外部还想对ViewPager进行这个方法的使用的时候就会出问题
     * 因此我们内部从新定义这个方法的实现
     */
    public interface PageOnchangeListener{

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        public void onPageSelected(int position);
        public void onPageScrollStateChanged(int state);
    }
    public void setOnPagerChangerListener(PageOnchangeListener listener){
        this.mListener=listener;
    }
    public HSCrollIndicator(Context context) {
        super(context);
    }

    public HSCrollIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
        ln=new ViewPagerIndicator(context);
        this.addView(ln);
    }

    public void setVisibleTabCount(int visibleTabCount) {
        ln.setVisibleTabCount(visibleTabCount);
    }

    public void setTabItemTitles(List<String> tabItemTitles) {
        ln.setTabItemTitles(tabItemTitles);
    }
    /**
     * 设置ViewPager
     * @param mViewPager
     * @param pos
     */
    public void setViewPager(ViewPager mViewPager, int pos) {
        ln.setViewPager( mViewPager,  pos);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //tabWidth*psitionOffest
                ln.scroll(position, positionOffset);
                scroll(position, positionOffset);
                if (mListener != null)
                    mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (mListener != null)
                    mListener.onPageSelected(position);
                ln.highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null)
                    mListener.onPageScrollStateChanged(state);
            }
        });
        mViewPager.setCurrentItem(pos);
        ln.highLightTextView(pos);
    }
    //指示器跟随手机进行移动
    public void scroll(int position, float positionOffset) {
        int tabWidth =ln.getScreenWidth() /ln.mTabVisableCount;

        //容器移动，当tab处于移动的最后一个时候
        if(position>=(ln.mTabVisableCount-1)&&positionOffset>0){
            //  mHScrollView.smoothScrollTo((position-(mTabVisableCount-1))*tabWidth+(int)(tabWidth*positionOffset),0);
            this.smoothScrollTo((position - (ln.mTabVisableCount - 1)) * tabWidth + (int) (tabWidth * positionOffset),0);
        }

        invalidate();//便宜完成调用重回方法

    }

}
