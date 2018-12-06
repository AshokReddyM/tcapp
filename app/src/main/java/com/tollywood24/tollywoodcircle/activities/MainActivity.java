package com.tollywood24.tollywoodcircle.activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.tollywood24.tollywoodcircle.NetworkUtil;
import com.tollywood24.tollywoodcircle.NotificationHelper;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.ShowNotificationJob;
import com.tollywood24.tollywoodcircle.Upload;
import com.tollywood24.tollywoodcircle.adapters.PagerAdapter;
import com.tollywood24.tollywoodcircle.pojo.NotificationService;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Invitation";
    @BindView(R.id.add_website_layout)
    RelativeLayout addWebsiteLayout;

    @BindView(R.id.feedback_layout)
    RelativeLayout feedbackLayout;

    @BindView(R.id.rate_app_layout)
    RelativeLayout rating;


    @BindView(R.id.about_layout)
    RelativeLayout aboutLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.addresses_tab_layout)
    TabLayout tabs;

    @BindView(R.id.address_viewPager)
    ViewPager pager;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.nav)
    ImageView nav;

    @BindView(R.id.search)
    ImageView search_menu;

    Toolbar toolbar;
    private RewardedVideoAd mRewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        ShowNotificationJob.schedulePeriodic();

        MobileAds.initialize(MainActivity.this, "ca-app-pub-3940256099942544/1033173712)");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(MainActivity.this);
        mRewardedVideoAd.loadAd(getResources().getString(R.string.videoAd),
                new AdRequest.Builder().build());


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NotificationHelper.scheduleRepeatingElapsedNotification(MainActivity.this);


        Intent intent = getIntent();
        Upload dataSet = (Upload) intent.getSerializableExtra("dataSetBroadCast");


        if (dataSet != null) {
            String aux[] = dataSet.getLink().split("/");

            if (NetworkUtil.getConnectivityStatusString(this).equals("true")) {
                Intent intent1 = new Intent(MainActivity.this, PostDetailsActivity.class);
                intent.putExtra("post_link", dataSet.getLink());
                intent.putExtra("post_title", dataSet.getTitle());
                intent.putExtra("link", aux[1] + " " + aux[2]);
                intent.putExtra("image_link", dataSet.getImageUrl());
                intent.putExtra("desc", dataSet.getDescription());
                startActivity(intent1);
            } else {
                Toast.makeText(this, "Please Check Internet Connection and TRY AGAIN", Toast.LENGTH_SHORT).show();
            }
        }


        try {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);


            FragmentManager fm = getSupportFragmentManager();
            PagerAdapter adapter = new PagerAdapter(fm);
            pager.setAdapter(adapter);
            tabs.setupWithViewPager(pager);
            pager.setOffscreenPageLimit(3);


            nav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                }
            });


            search_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SearchByActorActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            });


            rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }


                    drawer.closeDrawer(GravityCompat.START);
                }
            });


            addWebsiteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.closeDrawer(GravityCompat.START);
                    sendMail("SUB: Your Question is here", "Mail description is here");
                }
            });


            feedbackLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.closeDrawer(GravityCompat.START);
                    sendMail("SUB: Related to Feedback", "Your feedback is here");

                }
            });


            ScheduledExecutorService scheduler =
                    Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new Runnable() {

                public void run() {
                    Log.i("hello", "world");
                    runOnUiThread(new Runnable() {
                        public void run() {

                            if (mRewardedVideoAd.isLoaded()) {
                                mRewardedVideoAd.show();
                            }

                            mRewardedVideoAd.loadAd(getResources().getString(R.string.videoAd),
                                    new AdRequest.Builder().build());

                        }
                    });

                }
            }, 1000, 1000, TimeUnit.SECONDS);


            aboutLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    drawer.closeDrawer(GravityCompat.START);

                    PackageInfo pInfo = null;
                    try {
                        pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    String version = pInfo.versionName;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Tollywood Circle");
                    builder.setMessage("App version : " + version);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            builder.create();
                            builder.create().cancel();

                        }
                    });
                    builder.show();
                }
            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        AdView mAdView = (AdView) findViewById(R.id.adView_player);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you want to exit ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("no", null).show();

    }


    public void sendMail(String s1, String s) {

        Log.i("Send email", "");
        String[] TO = {"aaashokreddy@gmail.com"};
        String[] CC = {""};

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:aaashokreddy@gmail.com?subject=" + s1 + "&body=" + s);
        intent.setData(data);
        startActivity(intent);

        Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    protected void onDestroy() {
        super.onDestroy();


    }


    @Override
    protected void onResume() {
        mRewardedVideoAd.pause(this);
        super.onResume();

    }


    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }


}
