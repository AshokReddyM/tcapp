package com.tollywood24.tollywoodcircle.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.tollywood24.tollywoodcircle.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhijeet on 2/4/17.
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @ApplicationContext
    Context providesContext() {
        return mFragment.getActivity();
    }





    /*@Provides
    SiteDashBoardPagerAdapter provideSiteDashBoardPagerAdapter() {
        return new SiteDashBoardPagerAdapter(mFragment.getFragmentManager(), 2);
    }

    @Provides
    MaterialModuleFragmentStatePagerAdapter provideMaterialModuleFragmentStatePagerAdapter(){
        return new MaterialModuleFragmentStatePagerAdapter(mFragment.getFragmentManager(),4);
    }
*/


}
