package com.sh.lynn.hz.lehe.module.joker;

import com.sh.lynn.hz.lehe.base.PreferencesManager;
import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hyz84 on 16/11/11.
 */

public class JokerPresenter implements JokerContract.UserActionsListener {

    private APIManager mAPIManager;
    private JokerContract.View mView;
    PreferencesManager mPreferencesManager;
    @Inject
    JokerPresenter(APIManager apiManager,JokerContract.View view,PreferencesManager preferencesManager){
        mAPIManager = apiManager;
        mView =view;
        mPreferencesManager=preferencesManager;
    }

    @Override
    public void getJokers() {
        int index =mPreferencesManager.getCurJokerIndex();
        int total = mPreferencesManager.getJokerTotal();
        if(index>total){
            return;
        }
       mAPIManager.getJokers(mPreferencesManager, new SimpleCallback<List<Joker>>() {
           @Override
           public void onStart() {

           }

           @Override
           public void onNext(List<Joker> jokers) {
               mView.showJokerList(jokers);
           }

           @Override
           public void onComplete() {
mView.closeLoading();
           }
       });
    }
}
