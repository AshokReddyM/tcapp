package com.tollywood24.tollywoodcircle.ui.news.news_list.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tollywood24.tollywoodcircle.data.model.CategoryResponse;
import com.tollywood24.tollywoodcircle.ui.news.news_list.fragment.DynamicNewsFragment;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private final ArrayList<CategoryResponse> categories;


    public SectionsPagerAdapter(FragmentManager fm, ArrayList<CategoryResponse> categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {

        return DynamicNewsFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }
}