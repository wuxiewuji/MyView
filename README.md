# MyView
![image](https://github.com/YourAcountName/ProjectName/blob/master/GIFName.gif ) 

xml布局使用方法
  
     <com.xxf.myviewutils.customview.Topbar
        android:id="@+id/custom_tv"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        app:leftIvDrawable="@mipmap/icon_fanhui_bai"
        app:leftText="你好"
        app:leftTextColor="@color/colorText"
        app:leftDrawable="@color/colorAccent"
        app:rightDrawable="@color/colorBtn"
        app:rightIvDrawable="@mipmap/icon_genduo"
        app:rightText="你好么"
        app:rightTextColor="@color/colorText"
        app:rightTextSize="14px"
        app:tvTitleText="我在中间"
        app:tvTitleTextColor="@color/colorText"
        app:tvTitleTextSize="12px"
        app:leftTextSize="15px"
        app:leftVisibility="true"
        app:rightVisibility="true"
        />
        注*xmlns:app="http://schemas.android.com/apk/res-auto"  获取资源引用，一定要有
java代码

        Topbar customTv = (Topbar) findViewById(R.id.custom_tv);
        customTv.setLeftIsvisable(true);//设置左边的显示，默认显示可以不用设置
        customTv.setRIsvisable(false);//设置右边的隐藏
        //设置左边和右边LinearLayout的监听事件
        customTv.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {//左边
                Toast.makeText(TopbarAcivity.this, "你点击了左边", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {//右边
                Toast.makeText(TopbarAcivity.this, "你点击了→_→", Toast.LENGTH_SHORT).show();
            }
        });
