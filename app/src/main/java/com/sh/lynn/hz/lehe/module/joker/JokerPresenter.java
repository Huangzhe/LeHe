package com.sh.lynn.hz.lehe.module.joker;

import com.sh.lynn.hz.lehe.base.PreferencesManager;
import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hyz84 on 16/11/11.
 */

public class JokerPresenter implements JokerContract.UserActionsListener {

    private APIManager mAPIManager;
    private JokerContract.View mView;
    PreferencesManager mPreferencesManager;
    DaoSession mDaoSession;
    private RxDao<Joker, String> jokerDao;
    private RxQuery<Joker> jokerQuery;

    @Inject
    JokerPresenter(APIManager apiManager, JokerContract.View view, PreferencesManager preferencesManager, DaoSession daoSession) {
        mAPIManager = apiManager;
        mView = view;
        mPreferencesManager = preferencesManager;
        mDaoSession = daoSession;
        jokerDao = mDaoSession.getJokerDao().rx();
        jokerQuery = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct).rx();
    }

    @Override
    public void getJokers() {
        int index = mPreferencesManager.getCurJokerIndex();
        int total = mPreferencesManager.getJokerTotal();
        if (index > total) {
            loadMoreJokers(index - 20 > 0 ? index - 20 : 0);
            return;
        }
        mAPIManager.getJokers(mPreferencesManager, new SimpleCallback<List<Joker>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(List<Joker> jokers) {
                mView.showJokerList(jokers);
                jokerDao.insertOrReplaceInTx(jokers);
            }

            @Override
            public void onComplete() {
                mView.closeLoading();
            }
        });
    }

    @Override
    public void loadMoreJokers(final int start) {
        jokerQuery.list().subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Joker>>() {

            @Override
            public void call(List<Joker> jokers) {
                mView.showJokerList(jokers.subList(start, start + 20));
            }
        });
    }
}
