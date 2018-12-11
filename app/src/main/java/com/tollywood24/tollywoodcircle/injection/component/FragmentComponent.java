package com.tollywood24.tollywoodcircle.injection.component;

import com.tollywood24.tollywoodcircle.injection.Perfragment;
import com.tollywood24.tollywoodcircle.injection.module.FragmentModule;
import com.tollywood24.tollywoodcircle.ui.landingpage.activity.LandingScreenActivity;
import com.tollywood24.tollywoodcircle.ui.local_video_player.ListFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.MovieNewsListFragment;

import dagger.Subcomponent;

/**
 * Created by arunbangar on 2/4/17.
 * to inject fragment into Fragment Component so
 * dependencies can be injected in each fragment making them a
 * container
 */
@Perfragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(MovieNewsListFragment movieNewsListFragment);
    void inject(ListFragment listFragment);
}
