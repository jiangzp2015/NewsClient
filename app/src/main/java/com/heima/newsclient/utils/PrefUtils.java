package com.heima.newsclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author SparkJzp
 * @time 2016/11/11 14:59
 * @ClassName PrefUtils
 * @Descrition 对SharedPreferences的封装
 */
public class PrefUtils {
    public static SharedPreferences getPref(Context contex) {
        return contex.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static boolean putBoolean(Context context, boolean value, String key) {
        return getPref(context).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPref(context).getBoolean(key, defValue);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }


    public static void putString(Context context, String value, String key) {
        getPref(context).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        return getPref(context).getString(key, defValue);
    }

    public static String getString(Context context, String key) {
        return getString(context, key,"");
    }

    public static void putInt(String key, int value, Context ctx) {
        getPref(ctx).edit().putInt(key, value).commit();
    }
    public static int getInt(String key, int defValue, Context ctx) {
        return getPref(ctx).getInt(key, defValue);
    }
    public static int getInt(String key,Context ctx) {
        return getPref(ctx).getInt(key, 0);
    }

    public static void remove(String key, Context ctx) {
        getPref(ctx).edit().remove(key).commit();
    }
}