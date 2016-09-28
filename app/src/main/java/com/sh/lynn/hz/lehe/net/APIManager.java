package com.sh.lynn.hz.lehe.net;

import android.app.Application;

import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharp;
import com.sh.lynn.hz.lehe.module.photos.Photos;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hyz84 on 16/9/8.
 */
public class APIManager {

    private final ApiService apiService;

    private final Application application;

    public APIManager(ApiService apiService, Application application) {
        this.apiService = apiService;
        this.application = application;
    }


    public void getBrainSharp(String key, SimpleCallback<List<BrainSharp.NewslistBean>> callback) {

        apiService.getBrainSharp(key).flatMap(new Func1<BrainSharp, Observable<List<BrainSharp.NewslistBean>>>() {
            @Override
            public Observable<List<BrainSharp.NewslistBean>> call(BrainSharp sharp) {
                if (sharp.getCode() != 200) {
                    return Observable.error(new Throwable(sharp.getMsg()));
                } else {
                    return Observable.just(sharp.getNewslist());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<BrainSharp.NewslistBean>>(callback, application));
    }

    public void getPhotos(String key, int page ,SimpleCallback<List<Photos>> callback) {

        apiService.getPhotos(key,page).flatMap(new Func1<BaseResponse<Photos>, Observable<List<Photos>>>() {
            @Override
            public Observable<List<Photos>> call(BaseResponse<Photos> photos) {
                if (photos.getCode() != 200) {
                    return Observable.error(new Throwable(photos.getMsg()));
                } else {
                    return Observable.just(photos.getNewslist());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Photos>>(callback, application));
    }


}
