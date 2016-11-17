package com.sh.lynn.hz.lehe.module.joyimage;

import com.sh.lynn.hz.lehe.base.PreferencesManager;
import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hyz84 on 16/11/11.
 */

public class JoyImagePresenter implements JoyImageContract.UserActionsListener {

    private APIManager mAPIManager;
    private JoyImageContract.View mView;
    PreferencesManager mPreferencesManager;
//    DaoSession mDaoSession;
//    private RxDao<Joker, String> jokerDao;
//    private RxQuery<Joker> jokerQuery;

    @Inject
    JoyImagePresenter(APIManager apiManager, JoyImageContract.View view, PreferencesManager preferencesManager) {
        mAPIManager = apiManager;
        mView = view;
        mPreferencesManager = preferencesManager;
//        mDaoSession = daoSession;
//        jokerDao = mDaoSession.getJokerDao().rx();
//        jokerQuery = mDaoSession.getJokerDao().queryBuilder().orderDesc(JokerDao.Properties.Ct).rx();
    }

    @Override
    public void getJoyImages() {
        int index = mPreferencesManager.getCurJoyGIFIndex();
        int total = mPreferencesManager.getJoyGIFTotal();
        if (index > total) {
            //loadMoreJokers(index - 20 > 0 ? index - 20 : 0);
          //  Toast.makeText(mView,"你已经看完了所有笑话，请过会儿再看~",Toast.LENGTH_LONG).show();

            mView. showEnd("没有更多了，请过会儿再来看~");
            return;
        }
        mAPIManager.getJoyGIF(mPreferencesManager, new SimpleCallback<List<JoyImage>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(List<JoyImage> joyImages) {
                mView.showJoyImageList(joyImages);
                //jokerDao.insertOrReplaceInTx(jokers);
            }

            @Override
            public void onComplete() {
                mView.closeLoading();
            }
        });
    }


}
