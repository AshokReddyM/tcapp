package com.tollywood24.tollywoodcircle;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tollywood24.tollywoodcircle.activities.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RssPullService extends IntentService {

    public static final String BROADCAST_ACTION = "com.tollywood24.tollywoodcircle.android.action.broadcast";

    private Upload message;

    public RssPullService() {
        super("RssPullService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Upload dataSet = updateNews();
        if (dataSet != null) {
            sendMyBroadCast(dataSet);
        }

        return START_STICKY;
    }


    private void sendMyBroadCast(Upload dataSet) {
        try {
            Intent broadCastIntent = new Intent();
            broadCastIntent.putExtra("dataSet", dataSet);
            broadCastIntent.setAction(BROADCAST_ACTION);
            sendBroadcast(broadCastIntent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public Upload updateNews() {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tollywoodnews");
        Query lastQuery = database.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    if (dataSnapshot.hasChildren()) {
                        message = noteDataSnapshot.getValue(Upload.class);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        return message;

    }


}






