package com.tollywood24.tollywoodcircle.injection.module;

import android.app.Application;
import android.content.Context;

import com.tollywood24.tollywoodcircle.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }


}
