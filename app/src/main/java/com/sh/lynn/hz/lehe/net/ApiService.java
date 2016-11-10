package com.sh.lynn.hz.lehe.net;

import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharp;
import com.sh.lynn.hz.lehe.module.joker.Joker;
import com.sh.lynn.hz.lehe.module.lines.Lines;
import com.sh.lynn.hz.lehe.module.photos.Photos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hyz84 on 16/9/8.
 */
public interface ApiService {

    public static String SERVER_URL = "http://apis.baidu.com/";

    //脑筋急转弯
    @GET("txapi/naowan/naowan")
    Observable<BrainSharp> getBrainSharp(@Header("apikey") String apikey);

    //笑话 易源
    @GET("showapi_open_bus/showapi_joke/joke_text")
    Observable<List<Joker>> getJokersYY(@Header("apikey") String apikey, @Query("page") String page);

    //http://api.tianapi.com/meinv/?key=870400af7aa7368475528367c434c959&num=10

    //笑话 @Query("rand") int rand, @Query("word") String word,@Query("page") int page
    @GET("http://api.tianapi.com/meinv/")
    Observable<BaseResponse<Photos>> getPhotos(@Query("key") String apikey, @Query("num") int num);
    //http://apis.baidu.com/acman/zhaiyanapi/tcrand

    //
    @GET("acman/zhaiyanapi/tcrand")
    Observable<Lines> getLines(@Header("apikey") String apikey, @Query("fangfa") String fangfa);

    @GET("acman/zhaiyanapi/tcrand")
    Call<Lines> getWords(@Header("apikey") String apikey, @Query("fangfa") String fangfa);
}
