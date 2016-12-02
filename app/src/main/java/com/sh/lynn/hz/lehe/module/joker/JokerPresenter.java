package com.sh.lynn.hz.lehe.module.joker;

import android.util.Log;

import com.sh.lynn.hz.lehe.base.PreferencesManager;
import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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

    List<Joker> jokerList = new ArrayList<>();

    @Inject
    JokerPresenter(APIManager apiManager, JokerContract.View view, PreferencesManager preferencesManager, DaoSession daoSession) {
        mAPIManager = apiManager;
        mView = view;
        mPreferencesManager = preferencesManager;
        mDaoSession = daoSession;
        jokerDao = mDaoSession.getJokerDao().rx();
        // jokerQuery = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct).rx();
    }

    @Override
    public void getJokers() {

        jokerList.clear();

        int index = mPreferencesManager.getCurJokerIndex();
        int total = mPreferencesManager.getJokerTotal();
        if (index > total) {
            //loadMoreJokers(index - 20 > 0 ? index - 20 : 0);
            //  Toast.makeText(mView,"你已经看完了所有笑话，请过会儿再看~",Toast.LENGTH_LONG).show();

            mView.showEnd("没有更多了，请过会儿再来看~");
            return;
        }
        mAPIManager.getJokersYY(mPreferencesManager, new SimpleCallback<List<Joker>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(List<Joker> jokers) {
                // mView.showJokerList(jokrs);
                for (int x = 0; x < jokers.size(); x++) {
                    jokers.get(x).setReadState(0);

                }
                jokerDao.getDao().insertOrReplaceInTx(jokers);
//                jokerDao.insertOrReplaceInTx(jokers).subscribe(new Action1<Iterable<Joker>>() {
//                    @Override
//                    public void call(Iterable<Joker> jokers) {
//
//                        Log.d("loadMoreJokers", "jokers= ");
//                    }
//                });

                jokerList.addAll(jokers);

            }

            @Override
            public void onComplete() {
                mView.closeLoading();
                loadDadaFromDB();

            }
        });
    }

    @Override
    public void loadMoreJokers() {
        jokerList.clear();
        final int index = 0;
        QueryBuilder<Joker> queryBuilder = jokerDao.getDao().queryBuilder();
        final long total = queryBuilder.where(JokerDao.Properties.ReadState.eq(0)).count();
        long all = jokerDao.getDao().count();
        Log.d("loadMoreJokers", "all= " + all + "  total=" + total);

        if (total > 20) {
            loadDadaFromDB();
        } else {
            getJokers();
        }
    }

    /**
     * 从数据库中选择10条笑话，3张图片，2张GIF
     *
     */
    private void loadDadaFromDB() {
        Observable<List<Joker>> jokerObser = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct)
                .where(JokerDao.Properties.Type.eq("1"))
                .where(JokerDao.Properties.ReadState.eq(0))
                .limit(10)
                .rx()
                .list();
        Observable<List<Joker>> imgObser = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct)
                .where(JokerDao.Properties.Type.eq("2"))
                .where(JokerDao.Properties.ReadState.eq(0))
                .limit(3)
                .rx()
                .list();
        Observable<List<Joker>> gifObser = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct)
                .where(JokerDao.Properties.Type.eq("3"))
                .where(JokerDao.Properties.ReadState.eq(0))
                .limit(2)
                .rx()
                .list();
//合并所有查询结果
        Observable.concat(jokerObser, imgObser, gifObser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Joker>>() {
                    @Override
                    public void onCompleted() {
                        mView.showJokerList(jokerList);
                        changJokerStatue(jokerList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Joker> jokers) {
                        Log.d("loadMoreJokers", "jokers= " + jokers.size());
                        jokerList.addAll(jokers);
                    }


                });
    }

    /**
     * 更改阅读状态
     *
     * @param jokers
     */
    private void changJokerStatue(List<Joker> jokers) {
        for (int x = 0; x < jokers.size(); x++) {
            jokers.get(x).setReadState(1);
        }
        jokerDao.getDao().updateInTx(jokers);
//        jokerDao.updateInTx(jokers).subscribe(new Action1<Iterable<Joker>>() {
//            @Override
//            public void call(Iterable<Joker> jokers) {
//
//            }
//        });
    }

}

