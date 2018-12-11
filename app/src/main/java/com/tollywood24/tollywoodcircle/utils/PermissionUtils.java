package com.tollywood24.tollywoodcircle.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.tollywood24.tollywoodcircle.BuildConfig;

/**
 * Created by ca6 on 25/4/18.
 */

public class PermissionUtils {

    private static final String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public static boolean isBuildVersionAboveMarshmallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }


    public static boolean hasPermission(Context activity, String permission) {
        if (isBuildVersionAboveMarshmallow()) {
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    public static void requestPermission(Activity activity, String singlePermissions, int REQUEST_CODE) {
        if (isBuildVersionAboveMarshmallow()) {
            activity.requestPermissions(new String[]{singlePermissions}, REQUEST_CODE);
        }
    }

    public static void requestAllPermissionsActivity(Activity activity) {
        if (isBuildVersionAboveMarshmallow()) {
            activity.requestPermissions(permissions, 1000);
        }
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static void openAppPermissionSettings(Context context) {
        Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
        context.startActivity(i);
    }

}



