package com.tollywood24.tollywoodcircle.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.fragments.TeluguMovieNewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Fragment {

    private static final String TAG = "Invitation";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewpager)
    ViewPager pager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_main2, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* Instantiate a ViewPager and a PagerAdapter. */
        SlidePagerAdapter mPagerAdapter = new SlidePagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(pager);
    }

    /* PagerAdapter class */
    public class SlidePagerAdapter extends FragmentPagerAdapter {

        private String[] titles = {"Breaking News", "Latest", "Politics", "Entertainment", "Technology"};


        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new TeluguMovieNewsFragment();
                case 1:
                    return new TeluguMovieNewsFragment();
                case 2:
                    return new TeluguMovieNewsFragment();
                case 3:
                    return new TeluguMovieNewsFragment();
                case 4:
                    return new TeluguMovieNewsFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


}
