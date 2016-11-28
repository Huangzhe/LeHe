package com.sh.lynn.hz.lehe.module.joker;

import android.util.Log;

import com.sh.lynn.hz.lehe.base.PreferencesManager;
import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        // jokerQuery = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct).rx();
    }

    @Override
    public void getJokers() {
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

                jokerDao.insertOrReplaceInTx(jokers).subscribe(new Action1<Iterable<Joker>>() {
                    @Override
                    public void call(Iterable<Joker> jokers) {
                        //Joker joker =  jokers.iterator().next();
                        Log.d("loadMoreJokers", "jokers= ");
                    }
                });
                QueryBuilder<Joker> queryBuilder = jokerDao.getDao().queryBuilder();
                long total = queryBuilder.where(JokerDao.Properties.ReadState.eq(0)).count();
                //如果没有未读的，直接显示出来
                if (total == 0) {
                    mView.showJokerList(jokers);
                    changJokerStatue(jokers);
                }
            }

            @Override
            public void onComplete() {
                mView.closeLoading();
            }
        });
    }

    @Override
    public void loadMoreJokers() {

        final int index = 0;
        QueryBuilder<Joker> queryBuilder = jokerDao.getDao().queryBuilder();
        final long total = queryBuilder.where(JokerDao.Properties.ReadState.eq(0)).count();
        long all = queryBuilder.count();
        Log.d("loadMoreJokers", "all= " + all + "  total=" + total);
        if (total > 20) {

            jokerQuery = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct).where(JokerDao.Properties.ReadState.eq(0)).limit(10).rx();

            jokerQuery.list()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Joker>>() {
                        @Override
                        public void call(List<Joker> jokers) {
                            Log.d("loadMoreJokers", "jokers= " + jokers.size());


                            // mView.showJoyImageList(jokers.subList(start, start + 20));
                            mView.showJokerList(jokers);
                            changJokerStatue(jokers);
                            //mPreferencesManager.saveJokerIndex(index + 20, serverTotal);
                        }
                    });
        } else {
            jokerQuery = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct).limit(10).offset(0).rx();

            jokerQuery.list()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Joker>>() {
                        @Override
                        public void call(List<Joker> jokers) {

                            // mView.showJoyImageList(jokers.subList(start, start + 20));
                            mView.showJokerList(jokers);
                            changJokerStatue(jokers);
                            //mPreferencesManager.saveJokerIndex(index + 20, serverTotal);
                        }
                    });
            getJokers();
        }
    }

    /**
     * 更改阅读状态
     * @param jokers
     */
    private void changJokerStatue(List<Joker> jokers) {
        for (int x = 0; x < jokers.size(); x++) {
            jokers.get(x).setReadState(1);
        }
        jokerDao.updateInTx(jokers).subscribe(new Action1<Iterable<Joker>>() {
            @Override
            public void call(Iterable<Joker> jokers) {

            }
        });
    }

}

