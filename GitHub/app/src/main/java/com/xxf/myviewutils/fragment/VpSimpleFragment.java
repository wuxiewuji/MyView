package com.xxf.myviewutils.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxf.myviewutils.R;

/**
 * Created by Administrator on 2016/5/16.
 */
public class VpSimpleFragment extends Fragment {
    private String mTitle;
    public static final String BUNDLE_TITLE="title";


    public VpSimpleFragment() {
    }

    public static VpSimpleFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(BUNDLE_TITLE,title);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            mTitle=bundle.getString(BUNDLE_TITLE);
        }
        TextView tv=new TextView(getActivity());
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.colorText));
        return tv;
    }
}
