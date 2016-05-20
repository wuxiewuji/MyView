package com.xxf.myviewutils.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Administrator on 2016/5/16.
 */
public class UtilsTool {
    //获取手机截图
    public static Bitmap generateSpringCard(Context context){
        //获取到整个布局的父布局
        View view = ((Activity) context).getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }
}
