package com.xxf.myviewutils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xxf.myviewutils.customview.Indicator.HSCrollIndicator;
import com.xxf.myviewutils.fragment.VpSimpleFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ViewPagerIndicator演示类
 */
public class IndicatorActivity extends AppCompatActivity {

    @Bind(R.id.indicator)
    HSCrollIndicator mIndicator;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private List<VpSimpleFragment> mContents=new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private List<String> mTitles= Arrays.asList("短信", "收藏", "推荐", "短信2", "收藏2", "推荐2", "短信3", "收藏3", "推荐3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        ButterKnife.bind(this);
        initDatas();
        mViewPager.setAdapter(mAdapter);
        mIndicator.setVisibleTabCount(4);
        mIndicator.setTabItemTitles(mTitles);
        mIndicator.setViewPager(mViewPager,0);

    }
    private void initDatas() {
        for(String title:mTitles){
            VpSimpleFragment fragment=VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }

}
