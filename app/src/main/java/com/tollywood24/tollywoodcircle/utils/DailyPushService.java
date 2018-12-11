package com.tollywood24.tollywoodcircle.utils;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

class DailyPushService extends IntentService {

    public DailyPushService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d("Service started", "DailyPushService");
    }
}
