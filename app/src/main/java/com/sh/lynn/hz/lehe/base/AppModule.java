package com.sh.lynn.hz.lehe.base;

import android.app.Application;

import com.sh.lynn.hz.lehe.LeHeApp;
import com.sh.lynn.hz.lehe.module.joker.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyz84 on 16/9/8.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }



    @Provides
    @Singleton
    public PreferencesManager provideSharedPreferences(){
        return  new PreferencesManager(application);
    }
    @Provides
    @Singleton
    public DaoSession provideDaoSession(){
        return  ((LeHeApp)application).getDaoSession();
    }
}
