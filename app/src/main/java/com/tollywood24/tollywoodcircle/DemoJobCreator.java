package com.tollywood24.tollywoodcircle;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by ca6 on 13/3/18.
 */

class DemoJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case ShowNotificationJob.TAG:
                return new ShowNotificationJob();
            default:
                return null;
        }
    }





}