package com.sh.lynn.hz.lehe.module.lines;

import android.util.Log;

import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscription;

/**
 * Created by hyz84 on 16/11/10.
 */

public class WelcPagePresenter {
    WelcPageView mWelcPageView;
    APIManager apiManager;
    private Timer mTimer;
    public WelcPagePresenter (WelcPageView view, APIManager apiManager){
        mWelcPageView = view ;
        this. apiManager=apiManager;

    }

    public void getWelcWord(){
        mTimer = new Timer();

       final Subscription subscription = apiManager.getLines(new SimpleCallback<Lines>() {
            @Override
            public void onStart() {
                mWelcPageView.showLoading();

            }

            @Override
            public void onNext(Lines lines) {
                if(lines!=null) {
                    Log.e("result", lines.getTaici());
                    mWelcPageView.setWelcWord(lines.getTaici());
                }
               // goToMainView();
            }

            @Override
            public void onComplete() {
                mWelcPageView.closedLoading();
            }
        });
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //apiManager.c
                subscription.unsubscribe();
                goToMainView();
            }
        },3000);
    }

    public void goToMainView(){
        mWelcPageView.gotoMainView();
    }

}
