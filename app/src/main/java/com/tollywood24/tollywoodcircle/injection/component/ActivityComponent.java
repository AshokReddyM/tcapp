package com.tollywood24.tollywoodcircle.injection.component;

import com.tollywood24.tollywoodcircle.injection.PerActivity;
import com.tollywood24.tollywoodcircle.injection.module.ActivityModule;
import com.tollywood24.tollywoodcircle.ui.landingpage.activity.LandingScreenActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LandingScreenActivity mainActivity);

}
