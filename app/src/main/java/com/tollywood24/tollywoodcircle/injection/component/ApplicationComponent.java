package com.tollywood24.tollywoodcircle.injection.component;

import android.app.Application;
import android.content.Context;

import com.tollywood24.tollywoodcircle.data.DataManager;
import com.tollywood24.tollywoodcircle.data.SyncService;
import com.tollywood24.tollywoodcircle.data.local.DatabaseHelper;
import com.tollywood24.tollywoodcircle.data.local.PreferencesHelper;
import com.tollywood24.tollywoodcircle.injection.ApplicationContext;
import com.tollywood24.tollywoodcircle.injection.module.ApplicationModule;
import com.tollywood24.tollywoodcircle.utils.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();

    Application application();

    PreferencesHelper preferencesHelper();

    DatabaseHelper databaseHelper();

    DataManager dataManager();

    RxEventBus eventBus();

}
