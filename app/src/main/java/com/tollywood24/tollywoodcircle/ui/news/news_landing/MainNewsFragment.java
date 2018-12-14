package com.tollywood24.tollywoodcircle.ui.news.news_landing;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.ui.base.BaseFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.adapter.SectionsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainNewsFragment extends BaseFragment {
    private static final String TAG = "Invitation";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.container)
    ViewPager pager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_news, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        pager.setAdapter(mSectionsPagerAdapter);
        tabs.setupWithViewPager(pager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate called", Toast.LENGTH_SHORT).show();
    }


}
