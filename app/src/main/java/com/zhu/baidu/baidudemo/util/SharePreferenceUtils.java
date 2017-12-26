package com.zhu.baidu.baidudemo.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;


import com.zhu.baidu.AppContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by Administrator
 */

public class SharePreferenceUtils {

    public static void save(String key, Object value) {
        SharedPreferences sharedPreferences = AppContext.appContext.getSharedPreferences(AppContext.appContext.getPackageName(),
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }


    public static Integer getInt(String key) {
        SharedPreferences sharedPreferences = AppContext.appContext.getSharedPreferences(AppContext.appContext.getPackageName(),
                Activity.MODE_PRIVATE);
        if (sharedPreferences != null) {
            int value = sharedPreferences.getInt(key, 0);
            return value;
        }
        return 0;
    }

    public static String getString(String key) {
        SharedPreferences sharedPreferences = AppContext.appContext.getSharedPreferences(AppContext.appContext.getPackageName(),
                Activity.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String value = sharedPreferences.getString(key, "");
            return value;
        }
        return "";
    }

    public static boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = AppContext.appContext.getSharedPreferences(AppContext.appContext.getPackageName(),
                Activity.MODE_PRIVATE);
        if (sharedPreferences != null) {
            boolean value = sharedPreferences.getBoolean(key, false);
            return value;
        }
        return false;
    }


    public static boolean setObjectToShare(Object object,
                                       String key) {
        SharedPreferences share = PreferenceManager
                .getDefaultSharedPreferences( AppContext.appContext);
        if (object == null) {
            SharedPreferences.Editor editor = share.edit().remove(key);
            return editor.commit();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        String objectStr = new String(Base64.encode(baos.toByteArray(),
                Base64.DEFAULT));
        try {
            baos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = share.edit();
        editor.putString(key, objectStr);
        return editor.commit();
    }

    public static Object getObjectFromShare( String key) {
        SharedPreferences sharePre = PreferenceManager
                .getDefaultSharedPreferences( AppContext.appContext);
        try {
            String wordBase64 = sharePre.getString(key, "");
            // 将base64格式字符串还原成byte数组
            if (wordBase64 == null || wordBase64.equals("")) { // 不可少，否则在下面会报java.io.StreamCorruptedException
                return null;
            }
            byte[] objBytes = Base64.decode(wordBase64.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            // 将byte数组转换成product对象
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
