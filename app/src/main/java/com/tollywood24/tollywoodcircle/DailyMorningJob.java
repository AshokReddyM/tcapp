package com.tollywood24.tollywoodcircle;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.tollywood24.tollywoodcircle.activities.MainActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.tollywood24.tollywoodcircle.ShowNotificationJob.TAG;

/**
 * Created by ca6 on 24/3/18.
 */

public class DailyMorningJob extends DailyJob {


    public static final String TAG = "DailySyncJob";

    public static void schedule() {
        if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) {
            // job already scheduled, nothing to do
            return;
        }


        JobRequest.Builder builder = new JobRequest.Builder(TAG);

        // run job between 1pm and 2pm
        DailyJob.schedule(builder, TimeUnit.HOURS.toMillis(13), TimeUnit.HOURS.toMillis(14));
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(Job.Params params) {
        // do something

        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), MainActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle("Android DailyJob Sample")
                .setContentText("Notification from Android Job Sample App.")
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_new)
                .setShowWhen(true)
                .setColor(Color.RED)
                .setLocalOnly(true)
                .build();

        NotificationManagerCompat.from(getContext())
                .notify(new Random().nextInt(), notification);

        return DailyJobResult.SUCCESS;
    }
}