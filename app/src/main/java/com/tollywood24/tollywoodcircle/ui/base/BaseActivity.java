package com.tollywood24.tollywoodcircle.ui.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.tollywood24.tollywoodcircle.Application;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.injection.component.ActivityComponent;
import com.tollywood24.tollywoodcircle.injection.component.ConfigPersistentComponent;
import com.tollywood24.tollywoodcircle.injection.component.DaggerConfigPersistentComponent;
import com.tollywood24.tollywoodcircle.injection.module.ActivityModule;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent>
            sComponentsMap = new LongSparseArray<>();
    private static final int LOCATION_REQUEST_CODE = 100;
    public Integer screenId;
    private HashMap<String, Object> metaDataHasMap;
    private ActivityComponent mActivityComponent;
    public long mActivityId;
    public MediaPlayer mediaPlayer;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mActivityId, null);

        if (configPersistentComponent == null) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(Application.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this, new FragmentActivity()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }


    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

}