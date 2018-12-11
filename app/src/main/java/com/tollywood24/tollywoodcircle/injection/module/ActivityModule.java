package com.tollywood24.tollywoodcircle.injection.module;

import android.app.Activity;
import android.content.Context;

import com.tollywood24.tollywoodcircle.injection.ActivityContext;
import com.tollywood24.tollywoodcircle.ui.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(BaseActivity baseActivity, Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
