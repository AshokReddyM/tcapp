package com.tollywood24.tollywoodcircle.ui.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.ui.landingpage.activity.LandingScreenActivity;
import com.tollywood24.tollywoodcircle.ui.news.main_page.MainNewsFragment;

public class
SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LandingScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);


    }


}
