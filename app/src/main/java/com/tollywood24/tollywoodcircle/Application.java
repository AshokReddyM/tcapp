package com.tollywood24.tollywoodcircle;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.tollywood24.tollywoodcircle.injection.component.ApplicationComponent;
import com.tollywood24.tollywoodcircle.injection.component.DaggerApplicationComponent;
import com.tollywood24.tollywoodcircle.injection.module.ApplicationModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Application extends android.app.Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        FirebaseApp.initializeApp(this);
    }

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
