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

    public void saveJoyImageIndex(int index,int total){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.JOY_IMAGE_INDEX,index);
        editor.putInt(Constant.JOY_IMAGE_TOTAL,total);
        editor.commit();
    }

    public void saveJoyGIFIndex(int index,int total){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.JOY_GIF_INDEX,index);
        editor.putInt(Constant.JOY_GIF_TOTAL,total);
        editor.commit();
    }


    public int getCurJoyImageIndex(){
        return sharedPreferences.getInt(Constant.JOY_IMAGE_INDEX,1);
    }

    public int getJoyImageTotal(){
        return sharedPreferences.getInt(Constant.JOY_IMAGE_TOTAL,200);
    }

    public int getCurJoyGIFIndex(){
        return sharedPreferences.getInt(Constant.JOY_GIF_INDEX,1);
    }

    public int getJoyGIFTotal(){
        return sharedPreferences.getInt(Constant.JOY_GIF_TOTAL,200);
    }

    public int getCurJokerIndex(){
        return sharedPreferences.getInt(Constant.JOKER_INDEX,1);
    }

    public int getJokerTotal(){
        return sharedPreferences.getInt(Constant.JOKER_TOTAL,200);
    }

}
