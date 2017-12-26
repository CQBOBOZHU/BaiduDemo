package com.zhu.baidu.baidudemo;

import android.app.Application;

import com.zhu.baidu.AppContext;


/**
 * Created by BoBoZhu on 2017/12/25.
 */

public class MyAppLication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.instance(this);
    }
}
