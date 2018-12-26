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
        apiManager.getLine("870400af7aa7368475528367c434c959", new SimpleCallback<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(String s) {
                Log.d("getWelcWord",s);
            }

            @Override
            public void onComplete() {

            }
        });
//       final Subscription subscription = apiManager.getLines(new SimpleCallback<Lines>() {
//            @Override
//            public void onStart() {
//                mWelcPageView.showLoading();
//
//            }
//
//            @Override
//            public void onNext(Lines lines) {
//                if(lines!=null) {
//                    Log.e("result", lines.getTaici());
//                    mWelcPageView.setWelcWord(lines.getTaici());
//                }
//               // goToMainView();
//            }
//
//            @Override
//            public void onComplete() {
//                mWelcPageView.closedLoading();
//            }
//        });

       // apiManager.getWords()
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//              subscription.unsubscribe();
               // call.cancel();
                goToMainView();
            }
        },3000);
    }

    public void goToMainView(){
        mWelcPageView.gotoMainView();
    }

}
