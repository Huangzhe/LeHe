package com.sh.lynn.hz.lehe.net;


import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by wanglj on 16/7/4.
 */

public class BaseResponseFunc<T> implements Func1<BaseResponse<T>, Observable<List<T>>> {


    @Override
    public Observable<List<T>> call(BaseResponse<T> tBaseResponse) {
        //遇到非200错误统一处理,将BaseResponse转换成您想要的对象
        if (tBaseResponse.getCode() != 200) {
            return Observable.error(new Throwable(tBaseResponse.getMsg()));
        }else{
            return Observable.just(tBaseResponse.getNewslist());
        }
    }
}
