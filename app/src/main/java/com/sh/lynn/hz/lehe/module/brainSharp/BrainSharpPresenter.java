package com.sh.lynn.hz.lehe.module.brainSharp;

import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.base.Constant;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import java.util.List;

/**
 * Created by hyz84 on 16/9/19.
 */
public class BrainSharpPresenter {

    BrainSharpView mBrainSharpView;
    APIManager apiManager;
    public BrainSharpPresenter (BrainSharpView view, APIManager apiManager){
        mBrainSharpView = view ;
       this. apiManager=apiManager;
    }

    public void getData(){
        apiManager.getBrainSharp(Constant.APIKEY, new SimpleCallback<List<BrainSharp.NewslistBean>>() {
            @Override
            public void onStart() {
                mBrainSharpView.showLoading();
            }

            @Override
            public void onNext(List<BrainSharp.NewslistBean> list) {
                mBrainSharpView.showList(list);
            }

            @Override
            public void onComplete() {
                mBrainSharpView.closedLoading();
            }
        });
    }
}
