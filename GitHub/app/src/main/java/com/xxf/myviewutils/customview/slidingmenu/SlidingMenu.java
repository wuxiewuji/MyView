package com.xxf.myviewutils.customview.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.xxf.myviewutils.R;


/**
 * 侧滑菜单
 * Created by Administrator on 2016/5/16.
 */
public class SlidingMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;//左边菜单
    private ViewGroup mContent;//主布局
    private int mScreenWidth;//屏幕的宽度

    private int mMenuWidth;

    private boolean once=false;

    //dp值
    private int mMenuRightPadding=50;//菜单与屏幕右侧的距离

    private  boolean isOpen;// 菜单是否打开
    /**
     * 我是用自定义属性时，调用整个方法
     * @param context
     */
    public SlidingMenu(Context context) {
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightPading:
                    mMenuRightPadding=a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics()));
                    break;
            }
        }

        a.recycle();

        setHorizontalScrollBarEnabled(false);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth=outMetrics.widthPixels;
        ;
    }

    /**
     * 设置ziView的宽和高
     * 设置自己的宽和高，有个mWapper.所以就不用设置自己的宽和高了
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once){
            mWapper= (LinearLayout) getChildAt(0);
            mMenu= (ViewGroup) mWapper.getChildAt(0);
            mContent= (ViewGroup) mWapper.getChildAt(1);
            //设置菜单与主布局的宽度,子View的宽和高
            mMenuWidth=mMenu.getLayoutParams().width=mScreenWidth-mMenuRightPadding;
            mContent.getLayoutParams().width=mScreenWidth;
            once=true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量，将menu隐藏
     * view给其孩子设置尺寸和位置时被调用。子view，包括孩子在内，
     * 必须重写onLayout(boolean, int, int, int, int)方法，
     * 并且调用各自的layout(int, int, int, int)方法。
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(mMenuWidth,0);//将Menu隐藏到我们的左侧
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();//这句话的意思是,隐藏在左边的宽度

                if (scrollX>=mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);//移动到隐藏菜单的位置
                    isOpen=false;
                }else{
                    this.smoothScrollTo(0,0);//移动到刚好显示的位置
                    isOpen=true;
                }

                return true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu(){
        if(isOpen) return;
            this.smoothScrollTo(0,0);
            isOpen=true;
    }
    /**
     * 关闭菜单
     */
    public void closeMenu(){
        if (!isOpen) return;
        this.smoothScrollTo(mMenuWidth,0);
        isOpen=false;
    }

    /**
     * 切换菜单的状态
     */
    public void toggle(){
        if(isOpen)
            closeMenu();
        else
            openMenu();
    }

    /**
     * HorizontalScrollView：内部自带滚动改变监听事件
     * @param l：左边隐藏偏移量
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;//得到1~0的偏移量，方便我们的东环

        /**
         * qq侧滑
         *  区别1：内容区域1.0~0.7缩放效果
         *  scale：1.0~0.0
         *  0.7+0.3*scale
         *
         *  区别2：菜单的偏移量需要修改
         *
         *  区别3：菜单的显示时有缩放以及透明度的变化
         *  缩放：0.7~1.0
         *  1.0-scale*0.3
         *  透明度0.6~1.0
         *  1.0-0.4*scale
         */
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.6f);
        float rightScale=0.7f+0.3f*scale;
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);
        ViewHelper.setScaleX(mMenu, 1.0F - scale * 0.3F);
        ViewHelper.setScaleY(mMenu,1.0F - scale * 0.3F);
        ViewHelper.setAlpha(mMenu,1.0F-scale);
    }
}
