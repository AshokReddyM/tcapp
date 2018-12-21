package com.tollywood24.tollywoodcircle.injection.component;

import com.tollywood24.tollywoodcircle.injection.Perfragment;
import com.tollywood24.tollywoodcircle.injection.module.FragmentModule;
import com.tollywood24.tollywoodcircle.ui.local_video_player.ListFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_landing.MainNewsFragment;
import com.tollywood24.tollywoodcircle.ui.news.news_list.fragment.DynamicNewsFragment;

import dagger.Subcomponent;

/**
 * to inject fragment into Fragment Component so
 * dependencies can be injected in each fragment making them a
 * container
 */
@Perfragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(ListFragment listFragment);

    void inject(DynamicNewsFragment dynamicNewsFragment);
    void inject(MainNewsFragment mainNewsFragment);
}
