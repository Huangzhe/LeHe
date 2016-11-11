package com.sh.lynn.hz.lehe.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wanglj on 16/7/4.
 */

public class PreferencesManager {


    private SharedPreferences sharedPreferences;


    public PreferencesManager(Application application){
         sharedPreferences = application.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
    }
    public void saveJokerIndex(int index,int total){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.JOKER_INDEX,index);
        editor.putInt(Constant.JOKER_TOTAL,total);
        editor.commit();
    }



    public int getCurJokerIndex(){
        return sharedPreferences.getInt(Constant.JOKER_INDEX,1);
    }

    public int getJokerTotal(){
        return sharedPreferences.getInt(Constant.JOKER_TOTAL,200);
    }

}
