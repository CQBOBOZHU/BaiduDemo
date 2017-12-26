package com.zhu.baidu.baidudemo.util;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhu.baidu.AppContext;
import com.zhu.baidu.BuildConfig;
import com.zhu.baidu.baidudemo.util.logInterceptor.Level;
import com.zhu.baidu.baidudemo.util.logInterceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/5/2.
 */

public class RetroFitUtil {

    private static Retrofit retrofit;



    public static Retrofit.Builder getBuilder() {
        return new Retrofit.Builder();
    }

    public static Retrofit createGsonService(String baseUrl) {
        initOkHttp();
        return retrofit = getBuilder().client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }




    static OkHttpClient.Builder builder;

    public static void initOkHttp() {
        builder = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(new Cache(AppContext.appContext.getCacheDir(), 50 * 1024 * 1024));
        builder.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("Front-end", "android")
                .build());
    }

    /**
     * @param observable
     * @param subscriber
     */
    public static void subScribe(Observable observable, Observer subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
