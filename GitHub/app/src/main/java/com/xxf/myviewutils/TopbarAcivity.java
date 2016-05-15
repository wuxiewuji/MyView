package com.xxf.myviewutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xxf.myviewutils.customview.Topbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopbarAcivity extends AppCompatActivity {

    @Bind(R.id.custom_tv)
    Topbar customTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbar_acivity);
        ButterKnife.bind(this);

        customTv.setLeftIsvisable(true);//设置左边的显示，默认显示可以不用设置
        customTv.setRIsvisable(false);//设置右边的隐藏
        //设置左边和右边LinearLayout的监听事件
        customTv.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {//左边
                Toast.makeText(TopbarAcivity.this,"你点击了左边",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {//右边
                Toast.makeText(TopbarAcivity.this,"你点击了→_→",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
