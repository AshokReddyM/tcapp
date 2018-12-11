package com.tollywood24.tollywoodcircle.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.tollywood24.tollywoodcircle.utils.NetworkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SyncService extends Service {

    @Inject
    DataManager mDataManager;
    private Disposable mDisposable;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }


    @Override
    public void onDestroy() {
        if (mDisposable != null) mDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                /*AndroidComponentUtil.toggleComponent(context, this.getClass(), false);*/
                context.startService(getStartIntent(context));
            }
        }
    }

}
