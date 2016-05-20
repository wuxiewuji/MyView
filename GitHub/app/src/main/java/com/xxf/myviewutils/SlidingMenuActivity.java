package com.xxf.myviewutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.xxf.myviewutils.customview.slidingmenu.SlidingMenu;

public class SlidingMenuActivity extends AppCompatActivity {

    private SlidingMenu view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);
        view = (SlidingMenu) findViewById(R.id.menu);
    }

    public void toggleMenu(View v){
        view.toggle();
    }
}
