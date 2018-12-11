package com.tollywood24.tollywoodcircle.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class AnimationUtil {


    /**
     * Circular reveal exit animation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void doCircularExitAnimation(final Context context, View view, int cx, int cy) {
        final int revealRadius = (int) Math.hypot(view.getWidth(), view.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, revealRadius);
        circularReveal.setDuration(android.R.integer.config_mediumAnimTime);
        circularReveal.setInterpolator(new AccelerateInterpolator());

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        circularReveal.start();


    }

    public static void rotateImageAnimation(View view) {

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(3000);
        rotate.setRepeatCount(Animation.INFINITE);
        view.startAnimation(rotate);
    }
    
}
