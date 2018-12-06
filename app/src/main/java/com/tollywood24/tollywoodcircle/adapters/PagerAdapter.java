package com.tollywood24.tollywoodcircle.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.tollywood24.tollywoodcircle.fragments.EpaperFragment;
import com.tollywood24.tollywoodcircle.fragments.NewsFragment;
import com.tollywood24.tollywoodcircle.fragments.TeluguMovieNewsFragment;


/**
 * Created by Trainee on 7/28/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment();

        switch (position) {

            case 0:
                fragment = new TeluguMovieNewsFragment();

                break;
            case 1:

                fragment = new EpaperFragment();

                break;
            case 2:
                fragment = new NewsFragment();
                break;


        }

        return fragment;

    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Movie News";
            case 1:
                return "E-Papers";
            case 2:
                return "Websites";
        }
        return null;

    }


}

