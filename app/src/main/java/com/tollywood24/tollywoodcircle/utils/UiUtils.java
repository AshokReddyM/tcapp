package com.tollywood24.tollywoodcircle.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Window;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UiUtils {

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void playVideo(Context context, String uri) {
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector((TrackSelection.Factory) new Handler()), new DefaultLoadControl());

        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(Integer.parseInt(uri)));
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(context);
        try {
            rawResourceDataSource.open(dataSpec);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return rawResourceDataSource;
            }
        };

        MediaSource audioSource = new ExtractorMediaSource(rawResourceDataSource.getUri(),
                factory, OggExtractor.FACTORY, null, null);
        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(audioSource);

        player.prepare(loopingMediaSource);
        player.setPlayWhenReady(true);

    }

}
