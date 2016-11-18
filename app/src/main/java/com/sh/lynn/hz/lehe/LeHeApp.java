package com.sh.lynn.hz.lehe;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sh.lynn.hz.lehe.base.AppComponent;
import com.sh.lynn.hz.lehe.base.AppModule;
import com.sh.lynn.hz.lehe.base.DaggerAppComponent;
import com.sh.lynn.hz.lehe.module.joker.DaoMaster;
import com.sh.lynn.hz.lehe.module.joker.DaoSession;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.greendao.database.Database;

/**
 * Created by hyz84 on 16/9/8.
 */
public class LeHeApp  extends Application{
    private AppComponent appComponent;
    private DaoSession daoSession;
    public static LeHeApp get(Context context){
        return (LeHeApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        Fresco.initialize(this);

        //greenDao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "LeHe-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

        UMShareAPI.get(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
    public DaoSession getDaoSession(){
        return  daoSession;
    }
}
