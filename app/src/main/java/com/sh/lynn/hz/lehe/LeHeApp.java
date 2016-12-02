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
       // helper.onUpgrade(db,1,2);
        PlatformConfig.setWeixin("wxa2c6d123c9a0cc62", "515f10686ae25d90abf362e1865672cd");
      //  PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
      //  PlatformConfig.setQQZone("1105761827", "2RRZIS1GwPGDjA5L");

        UMShareAPI.get(this);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
    public DaoSession getDaoSession(){
        return  daoSession;
    }
}
