package com.tollywood24.tollywoodcircle.ui.news.news_list.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tollywood24.tollywoodcircle.ui.news.news_list.fragment.DynamicNewsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {"Breaking News", "Entertainment", "Politics", "Technology", "National", "Gallery"};

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return DynamicNewsFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}