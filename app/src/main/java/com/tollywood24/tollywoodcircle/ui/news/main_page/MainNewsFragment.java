package com.tollywood24.tollywoodcircle.ui.news.main_page;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.ui.base.BaseFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.MovieNewsListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainNewsFragment extends BaseFragment {

    private static final String TAG = "Invitation";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewpager)
    ViewPager pager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_news, container, false);
        ButterKnife.bind(this, rootView);
        SlidePagerAdapter mPagerAdapter = new SlidePagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(pager);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* Instantiate a ViewPager and a PagerAdapter. */


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
                    return new MovieNewsListFragment();
                case 1:
                    return new MovieNewsListFragment();
                case 2:
                    return new MovieNewsListFragment();
                case 3:
                    return new MovieNewsListFragment();
                case 4:
                    return new MovieNewsListFragment();
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
