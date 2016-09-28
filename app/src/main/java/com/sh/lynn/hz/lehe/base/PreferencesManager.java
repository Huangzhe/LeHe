package com.sh.lynn.hz.lehe.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wanglj on 16/7/4.
 */

public class PreferencesManager {

    public static final String PREFERENCES_NAME = "MYLEHEDAT";
    private SharedPreferences sharedPreferences;


    public PreferencesManager(Application application){
         sharedPreferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


}
