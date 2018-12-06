package com.tollywood24.tollywoodcircle.pojo;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.Upload;
import com.tollywood24.tollywoodcircle.activities.MainActivity;
import com.tollywood24.tollywoodcircle.activities.PostDetailsActivity;

/**
 * Created by ca6 on 20/3/18.
 */

public class NotificationService extends Service {

    private Upload message;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tollywoodnews");
        Query lastQuery = database.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    if (dataSnapshot.hasChildren()) {
                        message = noteDataSnapshot.getValue(Upload.class);
                        showCustomNotification(getApplicationContext());

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        return Service.START_NOT_STICKY;
    }


    public void showCustomNotification(Context context) {

        if (message != null) {

            Log.d("Notification Service", String.valueOf(message));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.customnotification);
            Intent BroadIntent = new Intent(context, PostDetailsActivity.class);
            BroadIntent.putExtra("tag", "1");
            BroadIntent.putExtra("dataSetBroadCast", message);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, BroadIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_new)
                    .setTicker(message.getTitle())
                    .setAutoCancel(true)
                    .setContentIntent(pIntent)
                    .setContent(remoteViews);


            Bitmap bitmap = getBitmapFromURL(context, message.getImageUrl());
            if (bitmap != null) {
                remoteViews.setImageViewBitmap(R.id.imagenotileft, bitmap);
            }
            remoteViews.setTextViewText(R.id.title, message.getTitle());
            String aux[] = message.getLink().split("/");
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationmanager.notify(0, builder.build());


        }
    }

    public Bitmap getBitmapFromURL(Context context, String src) {

        final Bitmap[] b = {null};

        Picasso.with(context)
                .load(src)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        b[0] = bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        return b[0];
    }


}
