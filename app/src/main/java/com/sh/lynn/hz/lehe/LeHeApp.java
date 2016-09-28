package com.sh.lynn.hz.lehe;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sh.lynn.hz.lehe.base.AppComponent;
import com.sh.lynn.hz.lehe.base.AppModule;
import com.sh.lynn.hz.lehe.base.DaggerAppComponent;

/**
 * Created by hyz84 on 16/9/8.
 */
public class LeHeApp  extends Application{
    private AppComponent appComponent;

    public static LeHeApp get(Context context){
        return (LeHeApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        Fresco.initialize(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
