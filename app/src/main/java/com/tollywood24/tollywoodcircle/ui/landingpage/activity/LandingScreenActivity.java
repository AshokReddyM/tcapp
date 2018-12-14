package com.tollywood24.tollywoodcircle.ui.landingpage.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.ui.base.BaseActivity;
import com.tollywood24.tollywoodcircle.ui.local_video_player.ListFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_landing.MainNewsFragment;
import com.tollywood24.tollywoodcircle.ui.online.video_landing_page.fragment.YoutubeVideosFragment;
import com.tollywood24.tollywoodcircle.ui.profile.ProfileFragment;

import java.util.ArrayList;

public class LandingScreenActivity extends BaseActivity {

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    // UI
    private AHBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_landing_page);

        bottomNavigation = findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_newspaper, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_online_video, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_local_video, R.color.color_tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_user, R.color.color_tab_4);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);


        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.restoreBottomNavigation(false);
        bottomNavigation.setColored(false);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setSelectedBackgroundVisible(true);

        bottomNavigation.setCurrentItem(0);
        fragmentReplace(new MainNewsFragment(), "news");

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        fragmentReplace(new MainNewsFragment(), "news");
                        break;
                    case 1:
                        fragmentReplace(new YoutubeVideosFragment(), "online videos");
                        break;
                    case 2:
                        fragmentReplace(new ListFragment(), "local videos");
                        break;
                    case 3:
                        fragmentReplace(new ProfileFragment(), "profile");
                        break;

                }
                return true;
            }
        });

    }


    public void fragmentReplace(Fragment fragment, String fragment_name) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, fragment_name);
        ft.commit();
    }


}
