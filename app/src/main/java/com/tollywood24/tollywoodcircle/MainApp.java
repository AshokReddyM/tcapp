package com.tollywood24.tollywoodcircle;

import android.app.Application;

import com.evernote.android.job.JobManager;

/**
 * Created by ca6 on 13/3/18.
 */

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new DemoJobCreator());
    }
}
