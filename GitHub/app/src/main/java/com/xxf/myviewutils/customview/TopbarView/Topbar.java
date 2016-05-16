package com.xxf.myviewutils.customview.TopbarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxf.myviewutils.R;

/**
 * Created by Administrator on 2016/5/15.
 * Topbar组合控件的使用，自定义的头部控件
 * 左边LinearLayout:ImageView+TextView,实现点击事件
 * 右边LinearLayout：Textview+ImageView,实现点击事件
 * 中间TextView：主要内容
 */
public class Topbar extends RelativeLayout {
    //声明这些单个的控件
    private LinearLayout leftLinearLayout, rightLinearLayout;
    private ImageView leftImage, rightImage;
    private TextView tvLeft, tvRight, tvTitle;

    //左边
    private int leftTextColor;
    private String leftText;
    private Drawable ivLeft;
    private Drawable leftBackground;
    private float leftTextSize;
    //右边
    private int rightTextColor;
    private String rightText;
    private Drawable ivRight;
    private Drawable rightBackground;
    private float rightTextSize;
    //中间TextView
    private int tvtitleTextColor;
    private float tvtitleTextSize;
    private String tvtitle;
    //布局属性
    private RelativeLayout.LayoutParams leftParams, rightParams, titleParams;
    //这里一定要转换成对应的LinearLayout.LayoutParams类型，否则没有gravity属性
    private LinearLayout.LayoutParams leftIvParams,leftTvParams, rightIvParams,rightTvParams;

    //////////////////////////////设置左边和右边点击事件的回掉////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public interface  topbarClickListener{
        public void leftClick();
        public void rightClick();
    }

    private topbarClickListener listener;

    public void setOnTopbarClickListener(final topbarClickListener listener){
        this.listener=listener;
        //为左边，右边LinearLayout增加点击事件,并且暴露出去
        if (listener!=null){
            leftLinearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.leftClick();
                }
            });
            rightLinearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.rightClick();
                }
            });
        }
    }


    public Topbar(Context context) {
        this(context, null);
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);
        //获取初始化值
        init(ta,context);
    }
    private boolean leftIsVisable,rightIsVisable;
    private void init(TypedArray ta, Context context) {
        //左边属性赋值
        leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor, 0);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftDrawable);
        leftText = ta.getString(R.styleable.Topbar_leftText);
        ivLeft = ta.getDrawable(R.styleable.Topbar_leftIvDrawable);
        leftTextSize = ta.getDimensionPixelSize(R.styleable.Topbar_leftTextSize, 14);

        //右边属性赋值
        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor, 0);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightDrawable);
        rightText = ta.getString(R.styleable.Topbar_rightText);
        ivRight = ta.getDrawable(R.styleable.Topbar_rightIvDrawable);
        rightTextSize = ta.getDimensionPixelSize(R.styleable.Topbar_rightTextSize, 14);
        //中间TextView
        tvtitleTextColor = ta.getColor(R.styleable.Topbar_tvTitleTextColor, 0);
        tvtitleTextSize = ta.getDimensionPixelSize(R.styleable.Topbar_tvTitleTextSize, 14);
        tvtitle = ta.getString(R.styleable.Topbar_tvTitleText);
        leftIsVisable=ta.getBoolean(R.styleable.Topbar_leftVisibility, true);
        rightIsVisable=ta.getBoolean(R.styleable.Topbar_rightVisibility, true);

        Log.i("inf", leftTextSize + "==" + rightTextSize + "==" + tvtitleTextSize);
        ta.recycle();//资源回收，放在资源浪费和不必要的错误

        //初始化
        leftLinearLayout=new LinearLayout(context);
        rightLinearLayout=new LinearLayout(context);
        leftImage=new ImageView(context);
        rightImage=new ImageView(context);
        tvLeft=new TextView(context);
        tvRight=new TextView(context);
        tvTitle=new TextView(context);
        setVerticalGravity(CENTER_VERTICAL);

        //控件赋值
        leftLinearLayout.setBackgroundDrawable(leftBackground);
        leftLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        leftLinearLayout.setVerticalGravity(CENTER_VERTICAL);
        leftImage.setImageDrawable(ivLeft);
        tvLeft.setText(leftText);
        tvLeft.setTextColor(leftTextColor);
        tvLeft.setTextSize(leftTextSize);

        rightLinearLayout.setBackgroundDrawable(rightBackground);
        rightLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        rightLinearLayout.setVerticalGravity(CENTER_VERTICAL);
        rightImage.setImageDrawable(ivRight);
        tvRight.setText(rightText);
        tvRight.setTextColor(rightTextColor);
        tvRight.setTextSize(rightTextSize);

        tvTitle.setText(tvtitle);
        tvTitle.setTextColor(tvtitleTextColor);
        tvTitle.setTextSize(tvtitleTextSize);
        tvTitle.setGravity(Gravity.CENTER);//设置textView居中
        tvTitle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        setBackgroundColor(getResources().getColor(R.color.titleBackground));//设置整体的背景颜色



        //设置左边LinearLayout内部控件的属性，添加如LinearLayout中

        leftIvParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        leftTvParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        leftIvParams.setMargins(10,0,0,0);
        leftIvParams.gravity=Gravity.CENTER_VERTICAL; leftTvParams.gravity=Gravity.CENTER_VERTICAL;
        leftLinearLayout.addView(leftImage, leftIvParams); leftLinearLayout.addView(tvLeft, leftTvParams);
        
        //设置左边LinearLayout的属性
        leftParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftLinearLayout, leftParams);

        //设置右边LinearLayout内部控件的属性，添加如LinearLayout中
        rightIvParams= new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightTvParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        rightIvParams.setMargins(0,0,10,0);
        rightIvParams.gravity=Gravity.CENTER_VERTICAL; rightTvParams.gravity=Gravity.CENTER_VERTICAL;
        rightLinearLayout.addView(tvRight,rightIvParams);rightLinearLayout.addView(rightImage,rightIvParams);

        //设置左边LinearLayout的属性
        rightParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightLinearLayout, rightParams);

        //设置中间TextView属性
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);//RelativeLayout布局的中间
        addView(tvTitle, titleParams);

        //设置左边和右边是否隐藏,默认显示
        if(leftIsVisable)
            leftLinearLayout.setVisibility(VISIBLE);
        else
            leftLinearLayout.setVisibility(GONE);
        if(rightIsVisable)
            rightLinearLayout.setVisibility(VISIBLE);
        else
            rightLinearLayout.setVisibility(GONE);



    }

    //暴露出去的代码设置左边是否显示
    public void setLeftIsvisable(boolean flag){

        if(flag)
            leftLinearLayout.setVisibility(VISIBLE);
        else
            leftLinearLayout.setVisibility(GONE);
    }
    //暴露出去的代码设置右边是否显示
    public void setRIsvisable(boolean flag){

        if(flag)
            rightLinearLayout.setVisibility(VISIBLE);
        else
            rightLinearLayout.setVisibility(GONE);
    }

}
