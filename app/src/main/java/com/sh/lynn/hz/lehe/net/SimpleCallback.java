package com.sh.lynn.hz.lehe.net;



public interface SimpleCallback<T> {
    void onStart();
    void onNext(T t);
    void onComplete();
}
