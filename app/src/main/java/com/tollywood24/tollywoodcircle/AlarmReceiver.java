package com.tollywood24.tollywoodcircle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.tollywood24.tollywoodcircle.activities.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;


public class AlarmReceiver extends BroadcastReceiver {



    Context context;
    String[] websitesFeedsArray;
    NotificationManager notificationManager;
    Intent repeatingIntent;
    Uri alarmSound;
    String mNotificationTitle;
    String mNotificationDesc;
    String mImageUrl;
    PendingIntent pendingIntent;
    String alarm_status;
    SharedPreferences sharedpreferences;
    private String MyPREFERENCES = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {


        this.context = context;

        Log.i("AlarmReceiver", "onReceive");
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        alarm_status = sharedpreferences.getString("key", null);

        try {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            repeatingIntent = new Intent(context, MainActivity.class);
            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            pendingIntent = PendingIntent.getBroadcast(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Intent intentService=new Intent(context, RssPullService.class);
            context.startService(intentService);


        } catch (Exception e) {
            e.printStackTrace();
        }


        ComponentName comp = new ComponentName(context.getPackageName(), RssPullService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

    }
}

