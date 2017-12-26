package com.zhu.baidu;

import android.content.Context;



/**
 * Created by Administrator on 2017/5/17.
 */

public class AppContext {


    public static Context appContext;

    private static AppContext appInstance;


    private AppContext(Context mContext) {
        this.appContext = mContext;
    }

    public static void instance(Context context) {
        if (appInstance == null) {
            synchronized (AppContext.class) {
                if (appInstance == null) {
                    appInstance = new AppContext(context);
                }
            }
        }
    }


}
