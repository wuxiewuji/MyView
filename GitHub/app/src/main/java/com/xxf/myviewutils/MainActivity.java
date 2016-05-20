package com.xxf.myviewutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xxf.myviewutils.customview.slidingmenu.SlidingMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.top_bar)
    Button topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.top_bar://自定义Topbar演示界面
                intent=new Intent(this,TopbarAcivity.class);
                break;
            case R.id.indicator://自定义ViewPagerIndicator演示页面
                intent=new Intent(this,IndicatorActivity.class);
                break;//自定义SlidingMeu
            case R.id.sliding_menu:
                intent=new Intent(this,SlidingMenuActivity.class);
                break;
        }
        if(intent!=null)
            startActivity(intent);
    }
}
