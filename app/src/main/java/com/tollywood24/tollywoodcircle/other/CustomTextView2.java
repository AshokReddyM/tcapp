package com.tollywood24.tollywoodcircle.other;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by trainee on 8/17/2017.
 */

public class CustomTextView2 extends TextView {

    private static Typeface mTypeface;

    public CustomTextView2(final Context context) {
        this(context, null);
    }

    public CustomTextView2(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView2(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Hind-SemiBold.ttf");
        }
        setTypeface(mTypeface);
    }

}
