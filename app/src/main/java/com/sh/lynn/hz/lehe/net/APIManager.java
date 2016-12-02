package com.sh.lynn.hz.lehe.net;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sh.lynn.hz.lehe.base.Constant;
import com.sh.lynn.hz.lehe.base.PreferencesManager;
import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharp;
import com.sh.lynn.hz.lehe.module.joker.Joker;
import com.sh.lynn.hz.lehe.module.joyimage.JoyImage;
import com.sh.lynn.hz.lehe.module.lines.Lines;
import com.sh.lynn.hz.lehe.module.photos.Photos;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
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

    public void getPhotos(String key, int page, SimpleCallback<List<Photos>> callback) {

        apiService.getPhotos(key, page).flatMap(new Func1<BaseResponse<Photos>, Observable<List<Photos>>>() {
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


    public Subscription getLines(SimpleCallback<Lines> callback) {
        return apiService.getLines(Constant.APIKEY, "json").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<Lines>(callback, application));
    }

    public Call<Lines> getWords(final SimpleCallback<Lines> callback) {
        Call<Lines> call = apiService.getWords(Constant.APIKEY, "json");

        call.enqueue(new Callback<Lines>() {
            @Override
            public void onResponse(Call<Lines> call, Response<Lines> response) {
                Log.e("result", response.body().getTaici());
                callback.onNext(response.body());

            }

            @Override
            public void onFailure(Call<Lines> call, Throwable t) {

            }
        });
        return call;
    }

    public Subscription getJokers(final PreferencesManager mPreferencesManager, SimpleCallback<List<Joker>> callback) {
        return apiService.getJokersYY(Constant.APIKEY, mPreferencesManager.getCurJokerIndex() + "")
                .flatMap(new Func1<JsonObject, Observable<List<Joker>>>() {
                    @Override
                    public Observable<List<Joker>> call(JsonObject json) {

                        if (json != null && json.get("showapi_res_code").getAsString().equals("0")) {

                            mPreferencesManager.saveJokerIndex(mPreferencesManager.getCurJokerIndex() + 1, json.getAsJsonObject("showapi_res_body").get("allPages").getAsInt());

                            JsonArray jokerArray = json.getAsJsonObject("showapi_res_body").getAsJsonArray("contentlist");

                            if (jokerArray != null) {
                                Gson gson = new Gson();
                                List<Joker> list = gson.fromJson(jokerArray.toString(), new TypeToken<List<Joker>>() {
                                }.getType());
                                return Observable.just(list);

                            } else {
                                return Observable.error(new Throwable("未获取到数据"));
                            }

                        }
                        return Observable.error(new Throwable(json == null ? "未获取到数据" : json.get("showapi_res_error").toString()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Joker>>(callback, application));
    }

    public Subscription getJokersYY(final PreferencesManager mPreferencesManager, SimpleCallback<List<Joker>> callback) {

        Observable<JsonObject> joyImageObservable = apiService.getJoyImage(Constant.SHOWAPI_APPID, Constant.SHOWAPI_SIGN, mPreferencesManager.getCurJoyImageIndex() + "", "5");
        Observable<JsonObject> joyGIFObservable = apiService.getJoyGIF(Constant.SHOWAPI_APPID, Constant.SHOWAPI_SIGN, mPreferencesManager.getCurJoyGIFIndex() + "", "5");
        Observable<JsonObject> joyObservable = apiService.getJokers(Constant.SHOWAPI_APPID, Constant.SHOWAPI_SIGN, mPreferencesManager.getCurJokerIndex() + "", "20");

        //合并三个请求结果
        return Observable.concat(joyImageObservable,joyObservable,joyGIFObservable)

                .flatMap(new Func1<JsonObject, Observable<List<Joker>> >() {
                    @Override
                    public Observable<List<Joker>>  call(JsonObject json) {

                        if (json != null && json.get("showapi_res_code").getAsString().equals("0")) {

                            mPreferencesManager.saveJokerIndex(mPreferencesManager.getCurJokerIndex() + 1, json.getAsJsonObject("showapi_res_body").get("allPages").getAsInt());

                            JsonArray jokerArray = json.getAsJsonObject("showapi_res_body").getAsJsonArray("contentlist");

                            if (jokerArray != null) {
                               //
                                Gson gson  =  new GsonBuilder().registerTypeAdapter(Joker.class,new Joker.JokerDeserializer()).create();

                                List<Joker> list = gson.fromJson(jokerArray.toString(), new TypeToken<List<Joker>>() {
                                }.getType());
                                return Observable.just(list);

                            } else {
                                return Observable.error(new Throwable("未获取到数据"));
                            }

                        }
                        return Observable.error(new Throwable(json == null ? "未获取到数据" : json.get("showapi_res_error").toString()));

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Joker>>(callback, application));
//        return apiService.getJokers(Constant.SHOWAPI_APPID, Constant.SHOWAPI_SIGN, mPreferencesManager.getCurJokerIndex() + "", "30")
//                .flatMap(new Func1<JsonObject, Observable<List<Joker>>>() {
//                    @Override
//                    public Observable<List<Joker>> call(JsonObject json) {
//
//                        if (json != null && json.get("showapi_res_code").getAsString().equals("0")) {
//
//                            mPreferencesManager.saveJokerIndex(mPreferencesManager.getCurJokerIndex() + 1, json.getAsJsonObject("showapi_res_body").get("allPages").getAsInt());
//
//                            JsonArray jokerArray = json.getAsJsonObject("showapi_res_body").getAsJsonArray("contentlist");
//
//                            if (jokerArray != null) {
//                                Gson gson = new Gson();
//                                List<Joker> list = gson.fromJson(jokerArray.toString(), new TypeToken<List<Joker>>() {
//                                }.getType());
//                                return Observable.just(list);
//
//                            } else {
//                                return Observable.error(new Throwable("未获取到数据"));
//                            }
//
//                        }
//                        return Observable.error(new Throwable(json == null ? "未获取到数据" : json.get("showapi_res_error").toString()));
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ExceptionSubscriber<List<Joker>>(callback, application));
    }


    public Subscription getJoyGIF(final PreferencesManager mPreferencesManager, SimpleCallback<List<JoyImage>> callback) {
        return apiService.getJoyImage(Constant.SHOWAPI_APPID, Constant.SHOWAPI_SIGN, mPreferencesManager.getCurJoyGIFIndex() + "", "5")
                .flatMap(new Func1<JsonObject, Observable<List<JoyImage>>>() {
                    @Override
                    public Observable<List<JoyImage>> call(JsonObject json) {

                        if (json != null && json.get("showapi_res_code").getAsString().equals("0")) {

                            mPreferencesManager.saveJoyGIFIndex(mPreferencesManager.getCurJoyGIFIndex() + 1, json.getAsJsonObject("showapi_res_body").get("allPages").getAsInt());

                            JsonArray jokerArray = json.getAsJsonObject("showapi_res_body").getAsJsonArray("contentlist");

                            if (jokerArray != null) {
                                Gson gson = new Gson();
                                List<JoyImage> list = gson.fromJson(jokerArray.toString(), new TypeToken<List<JoyImage>>() {
                                }.getType());
                                return Observable.just(list);

                            } else {
                                return Observable.error(new Throwable("未获取到数据"));
                            }

                        }
                        return Observable.error(new Throwable(json == null ? "未获取到数据" : json.get("showapi_res_error").toString()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<JoyImage>>(callback, application));
    }

    public Subscription downLoadImg(final String path, SimpleCallback<String> callback) {
        int index = path.lastIndexOf("/");
        final String fileName = path.substring(index);
        return apiService.downLoadImg(path)
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody response) {

                        String filePath = CommonUtils.writeResponseBodyToDisk(fileName, response);
                        Log.d("downLoadImage","downLoadImg:"+filePath);
                        return Observable.just(filePath);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback, application));
    }

    ;

}
