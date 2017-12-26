package com.zhu.baidu.baidudemo.util;

import android.widget.Toast;

import com.zhu.baidu.AppContext;


/**
 * Created by Administrator on 2016/11/25.
 */

public class ToastUtil {

    public static void show(Object obj) {
        Toast.makeText(AppContext.appContext, obj.toString(), Toast.LENGTH_SHORT).show();
    }


    public static void showLong(Object obj) {
        Toast.makeText(AppContext.appContext, obj.toString(), Toast.LENGTH_LONG).show();
    }
}
