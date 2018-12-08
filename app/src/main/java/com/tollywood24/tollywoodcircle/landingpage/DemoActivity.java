package com.tollywood24.tollywoodcircle.landingpage;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.fragments.TeluguMovieNewsFragment;
import com.tollywood24.tollywoodcircle.fragments.YoutubeVideosFragment;
import com.tollywood24.tollywoodcircle.main.MainActivity;
import com.tollywood24.tollywoodcircle.utils.UiUtils;

import java.util.ArrayList;

public class DemoActivity extends AppCompatActivity {

    private DemoFragment currentFragment;
    private DemoViewPagerAdapter adapter;
    private AHBottomNavigationAdapter navigationAdapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private boolean useMenuResource = true;
    private int[] tabColors;
    private Handler handler = new Handler();

    // UI
    private AHBottomNavigation bottomNavigation;
    private ViewPager mPager;
    private TabLayout mTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        UiUtils.setMiuiStatusBarDarkMode(this, true);

        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * Init UI
     */
    private void initUI() {


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
        bottomNavigation.restoreBottomNavigation(true);
        bottomNavigation.setColored(true);
        bottomNavigation.setSelectedBackgroundVisible(true);

        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        fragmentReplace(new MainActivity(), "news");
                        break;
                    case 1:
                        fragmentReplace(new YoutubeVideosFragment(), "online videos");
                        break;
                    case 2:
                        break;
                    case 3:
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
